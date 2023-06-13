package com.shop.eagleway.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.shop.eagleway.EaglewayApplication
import com.shop.eagleway.R
import com.shop.eagleway.data.EaglewayRepository
import com.shop.eagleway.data.ProductImage
import com.shop.eagleway.data.ProductInfo
import com.shop.eagleway.data.ProductInfoWithImages
import com.shop.eagleway.data.ProductPreview
import com.shop.eagleway.model.ImagePreview
import com.shop.eagleway.request.ProductData
import com.shop.eagleway.response.Category
import com.shop.eagleway.response.Currency
import com.shop.eagleway.response.MeasuringUnit
import com.shop.eagleway.utility.LoadingState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.UUID

data class AddProductUiState(val imagePreview: List<ImagePreview> = emptyList())

data class ProductUiState(val product: List<ProductInfoWithImages>)

data class ProductUiStateSingle(val product: ProductInfoWithImages?)

data class MeasuringUnitUiState(val unit: List<com.shop.eagleway.data.MeasuringUnit>)

data class CategoryUiState(val category: List<com.shop.eagleway.data.Category>)

data class CurrencyUiState(val currency: List<com.shop.eagleway.data.Currency>)


sealed interface ErrorState {
    object Null: ErrorState
    data class PriceError(val message: String): ErrorState
}

class ProductViewModel(
    private val repository: EaglewayRepository,
    private val context: Application
    ): ViewModel() {

    private val database: FirebaseDatabase = Firebase.database
    private val storage: FirebaseStorage = Firebase.storage

    private var userNum by mutableStateOf("")

    var addImageRowId by mutableStateOf(0L)
        private set

    var indexKey by mutableStateOf("")
        private set

    var isRefresh by mutableStateOf(false)
        private set

    var state by mutableStateOf(LoadingState.False)
        private set

    var imageAddError by mutableStateOf(false)
        private set
    fun resetImageAddError() {
        imageAddError = false
    }

    var nameField by mutableStateOf("")
        private set
    var nameFieldError: ErrorState by mutableStateOf(ErrorState.Null)
        private set
    fun updateNameField(text: String) {
        nameField = text
        if (nameField.isNotEmpty()) {
            nameFieldError = ErrorState.Null
        }
    }

    var salesPriceField by mutableStateOf(0)
        private set
    var salesPriceFieldError: ErrorState by mutableStateOf(ErrorState.Null)
        private set
    fun updateSalesPriceField(num: Int) {
        salesPriceField = num
        if (num > 0) {
            salesPriceFieldError = ErrorState.Null
        }
    }

    var mrpField by mutableStateOf(0)
        private set
    var mrpFieldError: ErrorState by mutableStateOf(ErrorState.Null)
        private set
    fun updateMrpField(num: Int) {
        mrpField = num
        if (num > 0) {
            mrpFieldError = ErrorState.Null
        }
    }

    var categoryField by  mutableStateOf("")
        private set
    var categoryFieldError: ErrorState by mutableStateOf(ErrorState.Null)
        private set
    fun updateCategoryField(category: String) {
        categoryField = category
        if (categoryField.isNotEmpty()) {
            categoryFieldError = ErrorState.Null
        }
    }

    var currencyField by mutableStateOf("")
        private set
    var currencyFieldError: ErrorState by mutableStateOf(ErrorState.Null)
        private set
    fun updateCurrencyField(currency: String) {
        currencyField = currency
        if (currencyField.isNotEmpty()) {
            currencyFieldError = ErrorState.Null
        }
    }

    var quantityField by mutableStateOf(0)
        private set
    fun updateQuantityField(num: Int) {
        quantityField = num
    }

    var measuringUnitField by mutableStateOf("")
        private set
    var measuringUnitError: ErrorState by mutableStateOf(ErrorState.Null)
    fun updateMeasuringField(unit: String) {
        measuringUnitField = unit
        if (measuringUnitField.isNotEmpty()) {
            measuringUnitError = ErrorState.Null
        }
    }

    var descriptionField by mutableStateOf("")
        private set
    var descriptionFieldError: ErrorState by mutableStateOf(ErrorState.Null)
        private set
    fun updateDescriptionField(text: String) {
        descriptionField = text
        if (descriptionField.isNotEmpty()) {
            descriptionFieldError = ErrorState.Null
        }
    }

    /**
     * Used in Add, Update product screen image preview
     * */
    var addProductUiState = MutableStateFlow(AddProductUiState())
        private set
    private fun resetAddProduct() {
        addProductUiState.value = AddProductUiState(listOf(ImagePreview(bitmap = null, isClickable = true)))
    }
    fun insertImagePreview(imagePreview: ImagePreview) {
        val list = mutableListOf<ImagePreview>().apply {
            add(imagePreview)
            addAll(addProductUiState.value.imagePreview)
        }

        addProductUiState.value = AddProductUiState(list)
    }

    fun updateImagePreview(imagePreview: ImagePreview)  {
        val list = mutableListOf<ImagePreview>().apply {
            add(imagePreview)
            addAll(addProductUiState.value.imagePreview)
            remove(ImagePreview(bitmap = null, isClickable = true))
        }
        addProductUiState.value = AddProductUiState(list)
    }

    fun updateImagePreview(context: Context, imagePreview: List<ProductImage>?) {
        val imageSize = imagePreview?.size

        val productImage = File(context.filesDir, "productImage")
        if (productImage.exists()) {
            when (imageSize) {
                6 -> {
                    val list = mutableListOf<ImagePreview>()

                    imagePreview.forEach {
                        val file = File(productImage, it.imageName.toString())
                        val bitmap = BitmapFactory.decodeFile(file.path)
                        list.add(ImagePreview(bitmap = bitmap, isClickable = false))
                    }

                    addProductUiState.value = AddProductUiState(list)
                }
                5 -> {
                    val list = mutableListOf<ImagePreview>()

                    imagePreview.forEach {
                        val file = File(productImage, it.imageName.toString())
                        val bitmap = BitmapFactory.decodeFile(file.path)
                        list.add(ImagePreview(bitmap = bitmap, isClickable = false))
                    }

                    list.add(ImagePreview(bitmap = null, isClickable = true))
                    addProductUiState.value = AddProductUiState(list)
                }
                4 -> {
                    val list = mutableListOf<ImagePreview>()

                    imagePreview.forEach {
                        val file = File(productImage, it.imageName.toString())
                        val bitmap = BitmapFactory.decodeFile(file.path)
                        list.add(ImagePreview(bitmap = bitmap, isClickable = false))
                    }

                    list.add(ImagePreview(bitmap = null, isClickable = true))
                    addProductUiState.value = AddProductUiState(list)
                }
                3 -> {
                    val list = mutableListOf<ImagePreview>()

                    imagePreview.forEach {
                        val file = File(productImage, it.imageName.toString())
                        val bitmap = BitmapFactory.decodeFile(file.path)
                        list.add(ImagePreview(bitmap = bitmap, isClickable = false))
                    }

                    list.add(ImagePreview(bitmap = null, isClickable = true))
                    addProductUiState.value = AddProductUiState(list)
                }
                2 -> {
                    val list = mutableListOf<ImagePreview>()

                    imagePreview.forEach {
                        val file = File(productImage, it.imageName.toString())
                        val bitmap = BitmapFactory.decodeFile(file.path)
                        list.add(ImagePreview(bitmap = bitmap, isClickable = false))
                    }

                    list.add(ImagePreview(bitmap = null, isClickable = true))
                    addProductUiState.value = AddProductUiState(list)
                }
                1 -> {
                    val list = mutableListOf<ImagePreview>()

                    imagePreview.forEach {
                        val file = File(productImage, it.imageName.toString())
                        val bitmap = BitmapFactory.decodeFile(file.path)
                        list.add(ImagePreview(bitmap = bitmap, isClickable = false))
                    }

                    list.add(ImagePreview(bitmap = null, isClickable = true))
                    addProductUiState.value = AddProductUiState(list)
                }
            }
        }
    }

    fun deleteImagePreview(imagePreview: ImagePreview) {
        val list = mutableListOf<ImagePreview>().apply {
            addAll(addProductUiState.value.imagePreview)
            remove(imagePreview)

            when (size) {
                5 -> {
                    if (contains(ImagePreview(bitmap = null, isClickable = true))) {
                        remove(ImagePreview(bitmap = null, isClickable = true))
                        add(ImagePreview(bitmap = null, isClickable = true))
                    } else {
                        add(ImagePreview(bitmap = null, isClickable = true))
                    }
                }
                4 -> {
                    remove(imagePreview)
                }
                3 -> { remove(imagePreview) }
                2 -> { remove(imagePreview) }
            }
        }

        addProductUiState.value = AddProductUiState(list)
    }

    val productUiState: StateFlow<ProductUiState> = repository.getProductWithImages().map {
        ProductUiState(product = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = ProductUiState(emptyList())
        )

    fun productUiStateSingle(id: Int) = repository.getProductWithImages(id).map {
        Log.d(TAG, it.toString())
        ProductUiStateSingle(product = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = ProductUiStateSingle(null)
    )

    val measuringUnitUiState: StateFlow<MeasuringUnitUiState> = repository.readMeasuringUnits().map {
        MeasuringUnitUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = MeasuringUnitUiState(emptyList())
    )

    val categoryUiState: StateFlow<CategoryUiState> = repository.readCategory().map {
        CategoryUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = CategoryUiState(emptyList())
    )

    val currencyUiState: StateFlow<CurrencyUiState> = repository.readCurrency().map {
        CurrencyUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = CurrencyUiState(emptyList())
    )

    fun saveProductInfo(bitmap: List<ImagePreview>, func: () -> Unit) {
        when {
            addProductUiState.value.imagePreview.size == 1 -> { imageAddError = true }
            nameField.isEmpty() -> { nameFieldError = ErrorState.PriceError("Name should not be empty") }
            nameField.length < 3 -> { nameFieldError = ErrorState.PriceError("Name should be at least 3 character") }
            salesPriceField <= 0 -> { salesPriceFieldError = ErrorState.PriceError("Price should not be zero") }
            mrpField <= 0 -> { mrpFieldError = ErrorState.PriceError("Price should not be zero") }
            (salesPriceField > mrpField) -> { salesPriceFieldError = ErrorState.PriceError("Price should be lower than MRP") }
            categoryField.isEmpty() -> { categoryFieldError = ErrorState.PriceError("Category should not empty") }
            currencyField.isEmpty() -> { currencyFieldError = ErrorState.PriceError("Currency should not empty") }
            measuringUnitField.isEmpty() -> { measuringUnitError = ErrorState.PriceError("Units should not empty") }
            descriptionField.isEmpty() -> { descriptionFieldError = ErrorState.PriceError("Description should not be empty") }
            else -> {
                state = LoadingState.True

                val productId = UUID.randomUUID().toString()

                val productData = ProductData(
                    name = nameField,
                    salesPrice = salesPriceField,
                    mrp = mrpField,
                    quantity = quantityField,
                    userNum = userNum,
                    productId = productId,
                    category = categoryField,
                    currency = currencyField,
                    measuringUnit = measuringUnitField,
                    description = descriptionField)

                val ref = database.getReference("productData")
                ref.push().setValue(productData)

                saveProductImage(bitmap, productId, func)
            }
        }
    }

    fun updateProductInfo(id: Int, productId: String, bitmap: List<ImagePreview>, func: () -> Unit) = viewModelScope.launch {
        when {
            addProductUiState.value.imagePreview.size == 1 -> { imageAddError = true }
            nameField.isEmpty() -> { nameFieldError = ErrorState.PriceError("Name should not be empty") }
            nameField.length < 3 -> { nameFieldError = ErrorState.PriceError("Name should be at least 3 character") }
            salesPriceField <= 0 -> { salesPriceFieldError = ErrorState.PriceError("Price should not be zero") }
            mrpField <= 0 -> { mrpFieldError = ErrorState.PriceError("Price should not be zero") }
            (salesPriceField > mrpField) -> { salesPriceFieldError = ErrorState.PriceError("Price should be lower than MRP") }
            categoryField.isEmpty() -> { categoryFieldError = ErrorState.PriceError("Category should not empty") }
            currencyField.isEmpty() -> { currencyFieldError = ErrorState.PriceError("Currency should not empty") }
            measuringUnitField.isEmpty() -> { measuringUnitError = ErrorState.PriceError("Units should not empty") }
            descriptionField.isEmpty() -> { descriptionFieldError = ErrorState.PriceError("Description should not be empty") }
            else -> {
                val ref = database.getReference("productData")

                state = LoadingState.True

                ref.orderByChild("productId").equalTo(productId).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {  data ->

                            val value = data.getValue(ProductData::class.java)

                            if (value?.productId == productId) {

                                val productInfo = ProductData(name = nameField, salesPrice = salesPriceField, mrp = mrpField, category = categoryField, currency = currencyField, quantity = quantityField, measuringUnit = measuringUnitField, userNum = value.userNum, productId = value.productId, description = descriptionField)
                                data.ref.setValue(productInfo)
                            }

                            state = LoadingState.False
                            func()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        isRefresh = false
                        state = LoadingState.False
                    }
                })

                saveProductImage(bitmap, productId) {}
            }
        }
    }

    //helper method for saveProductInfo()
    private fun saveProductImage(imagePreview: List<ImagePreview>, productId: String, func: () -> Unit) {
        val ref = storage.reference

        imagePreview.forEach {
            val productImageRef = ref.child("product_image_${userNum}/$productId/${UUID.randomUUID()}.jpg")

            val bytes = ByteArrayOutputStream()
            it.bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val data = bytes.toByteArray()

            val uploadTask = productImageRef.putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                Log.d(TAG, "Product image uploaded successfully")
                getProductInfo()
                state = LoadingState.False
                func()
            }.addOnFailureListener {
                    Log.e(TAG, it.message.toString())
                state = LoadingState.False
                }
        }
    }

    fun deleteProductImage(imageName: String) {
        storage.reference.child("product_image_$userNum/$imageName")
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "File has been deleted")
            }
            .addOnFailureListener {
                Log.e(TAG, it.message.toString())
            }
    }

    fun getProductInfo() {
        val ref = database.getReference("productData")

        ref.orderByChild("userNum").equalTo(userNum).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                refreshLocalDatabase()

                snapshot.children.forEach {  data ->
                    data.getValue(ProductData::class.java)?.apply {
                        Log.d(TAG, this.toString())
                        productId?.let { getProductImage(productId = it) }

                        val productInfo = ProductInfo(productName = name, salesPrice = salesPrice, mrp = mrp, quantity = quantity,productId = productId, category = category, currency = currency,measuringUnit = measuringUnit, description = description)

                        viewModelScope.launch {
                            repository.insertProductData(productInfo)
                        }
                    }

                    isRefresh = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                isRefresh = false
            }
        })
    }

    fun refreshLocalDatabase() = viewModelScope.launch {
        repository.deleteProductData()
        repository.deleteProductImage()
    }

    //helper method for getProductInfo()
    private fun getProductImage(productId: String) {
        val ref = storage.reference.child("product_image_${userNum}/$productId")
        ref.listAll().addOnSuccessListener {

            it.items.forEach { storageReference ->
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    Log.d(TAG, "Download Url: ${uri.path}")

                    val productImage = File(context.filesDir, "productImage")
                    productImage.mkdir()

                    if (productImage.exists()) {
                        val image = File(productImage, uri.path!!.split("$productId/")[1])
                        image.createNewFile()

                        if (image.exists()) {

                            val storageRef = storage.reference.child("product_image_${userNum}/$productId/${uri.path!!.split("$productId/")[1]}")
                            storageRef.getFile(image).addOnSuccessListener {

                                Log.d(TAG, "Successfully downloaded image file")

                                viewModelScope.launch {
                                    val imageName = uri.path!!.split("product_image_${userNum}/")[1].split("/")
                                    repository.insertProductImage(ProductImage(productId = imageName[0], imageName = imageName[1]))

                                    isRefresh = false
                                }

                            }.addOnFailureListener {
                                Log.e(TAG, "Error: " + it.message.toString())
                                isRefresh = false
                            }
                        }
                    }

                }.addOnFailureListener { failure ->
                    Log.e(TAG, failure.message.toString())
                    isRefresh = false
                }
            }

        }.addOnFailureListener {
            Log.e(TAG, it.message.toString())
            isRefresh = false
        }
    }

    fun deleteProductData() = viewModelScope.launch { 
        repository.deleteProductData()
    }

    fun onRefresh() {
        isRefresh = true
    }

    private fun getNumFromLocalDb() {
        userNum = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).getString("num", "") ?: ""
    }

    fun deleteProduct(productId: String) = viewModelScope.launch {
        repository.deleteProductData(productId)
        repository.deleteProductImage(productId)
        deleteProductFirebase(productId)
    }

    private fun deleteProductFirebase(productId: String) {
        val ref = database.getReference("productData")

        ref.orderByChild("productId").equalTo(productId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    val value =  it.getValue(ProductData::class.java)

                    if (productId == value?.productId) {
                        it.ref.removeValue()
                    }
                    Log.d(TAG, it.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    private fun getMeasuringUnitsDb() {
        val ref = database.getReference("measuringUnits")

        ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    it.getValue(MeasuringUnit::class.java)?.apply {
                        viewModelScope.launch {
                            repository.insertMeasuringUnits(com.shop.eagleway.data.MeasuringUnit(unitKey = unitKey, unitValue = unitValue))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

//    private fun getCategoryDb() {
//        val ref = database.getReference("category")
//
//        ref.addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.children.forEach {
//                    it.getValue(Category::class.java)?.apply {
//                        viewModelScope.launch {
//                            repository.insertCategory(com.shop.eagleway.data.Category(category = category))
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {  }
//        })
//    }

    fun saveCategory() = viewModelScope.launch {
        if (categoryField.length > 3) {
            repository.insertCategory(category = com.shop.eagleway.data.Category(category = categoryField))
        }
    }

    fun deleteCategory(category: com.shop.eagleway.data.Category) = viewModelScope.launch {
        repository.deleteCategory(category)
    }

    private fun getCurrencyDb() {
        val ref = database.getReference("currency")

        ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                refreshLocalCurrency()

                snapshot.children.forEach {
                    it.getValue(Currency::class.java)?.apply {
                        viewModelScope.launch {
                            repository.insertCurrency(com.shop.eagleway.data.Currency(currencyKey = currencyKey, currencyValue = currencyValue, currencyFlag = currencyFlag))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    private fun refreshLocalCurrency() = viewModelScope.launch {
        repository.deleteCurrency()
    }

    init {
        getNumFromLocalDb()
        getMeasuringUnitsDb()
//        getCategoryDb()
        getCurrencyDb()
        resetAddProduct()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val TAG = "ProductViewModel"
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EaglewayApplication)

                ProductViewModel(application.container.eaglewayRepository, application)
            }
        }
    }
}