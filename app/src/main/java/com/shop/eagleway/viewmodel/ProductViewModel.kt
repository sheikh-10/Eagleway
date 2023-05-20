package com.shop.eagleway.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.shop.eagleway.request.ProductData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.UUID

data class AddProductUiState(val productPreview: List<ProductPreview>)

data class ProductUiState(val product: List<ProductInfoWithImages>)

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
    fun updateMrpFieldError(num: Int) {
        mrpField = num
        if (num > 0) {
            mrpFieldError = ErrorState.Null
        }
    }

    var quantityField by mutableStateOf(0)
        private set
    fun updateQuantityField(num: Int) {
        quantityField = num
    }

    val addProductUiState: StateFlow<AddProductUiState> =
        repository.getImagePreview().map {
            AddProductUiState(productPreview = it)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = AddProductUiState(emptyList())
            )

    val productUiState: StateFlow<ProductUiState> = repository.getProductWithImages().map {
        ProductUiState(product = it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = ProductUiState(emptyList())
        )

    fun insertImagePreview(productPreview: ProductPreview) = viewModelScope.launch {
        repository.insertImagePreview(productPreview)
    }

    fun updateImagePreview(id: Long, productPreview: ProductPreview) = viewModelScope.launch {
        repository.updateImagePreview(id, productPreview)
    }

    fun deleteImagePreview(productPreview: ProductPreview) = viewModelScope.launch {
        repository.deleteImagePreview(productPreview)
    }

    private fun initialData() = viewModelScope.launch {
        addImageRowId = repository.insertImagePreview(ProductPreview(0, null, isClickable = true))
    }

    fun resetData() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun saveProductInfo(bitmap: List<ProductPreview>, func: () -> Unit) {
        when {
            addProductUiState.value.productPreview.size == 1 -> { imageAddError = true }
            nameField.isEmpty() -> { nameFieldError = ErrorState.PriceError("Name should not be empty") }
            nameField.length < 3 -> { nameFieldError = ErrorState.PriceError("Name should be at least 3 character") }
            salesPriceField <= 0 -> { salesPriceFieldError = ErrorState.PriceError("Price should not be zero") }
            mrpField <= 0 -> { mrpFieldError = ErrorState.PriceError("Price should not be zero") }
            (salesPriceField > mrpField) -> { salesPriceFieldError = ErrorState.PriceError("Price should be lower than MRP") }
            else -> {
                val productId = UUID.randomUUID().toString()

                val productData = ProductData(nameField, salesPriceField, mrpField, quantityField, userNum, productId)

                val ref = database.getReference("productData")
                ref.push().setValue(productData)

                saveProductImage(bitmap, productId, func)
            }
        }
    }

    //helper method for saveProductInfo()
    private fun saveProductImage(productPreview: List<ProductPreview>, productId: String, func: () -> Unit) {
        val ref = storage.reference

        productPreview.forEach {
            val productImageRef = ref.child("product_image_${userNum}/$productId/${UUID.randomUUID()}.jpg")

            val bytes = ByteArrayOutputStream()
            it.bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val data = bytes.toByteArray()

            val uploadTask = productImageRef.putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                Log.d(TAG, "Product image uploaded successfully")

                func()

            }.addOnFailureListener {
                    Log.e(TAG, it.message.toString())
                }
        }
    }

    fun getProductInfo() {
        val ref = database.getReference("productData")

        ref.orderByChild("userNum").equalTo(userNum).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {  data ->

//                    data.key?.let { indexKey = it }

                    data.getValue(ProductData::class.java)?.apply {
                        Log.d(TAG, this.toString())
                        productId?.let { getProductImage(productId = it) }

                        val productInfo = ProductInfo(productName = name, salesPrice = salesPrice, mrp = mrp, quantity = quantity,productId = productId)

                        viewModelScope.launch {
                            repository.insertProductData(productInfo)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                isRefresh = false
            }
        })
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

    init {
        initialData()
        getNumFromLocalDb()
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