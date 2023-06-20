package com.shop.eagleway.data

import kotlinx.coroutines.flow.Flow

interface EaglewayRepository {
    fun getImagePreview(): Flow<List<ProductPreview>>

    suspend fun insertImagePreview(productPreview: ProductPreview): Long

    suspend fun updateImagePreview(productPreview: ProductPreview)

    suspend fun updateImagePreview(id: Long, productPreview: ProductPreview)

    suspend fun deleteImagePreview(productPreview: List<ProductPreview>)

    suspend fun deleteImagePreview(productPreview: ProductPreview)

    fun getFilterData(): Flow<ProductPreview>

    suspend fun deleteAll()

    suspend fun insertProductData(productInfo: ProductInfo)

    suspend fun insertProductImage(productImage: ProductImage)

    fun getProductWithImages(): Flow<List<ProductInfoWithImages>>

    fun getProductWithImages(id: Int): Flow<ProductInfoWithImages>

    suspend fun updateProductData(id: Int, productName: String, salesPrice: Int, mrp: Int, quantity: Int, description: String)

    suspend fun deleteProductData()

    suspend fun deleteProductImage()

    suspend fun deleteProductData(productId: String)

    suspend fun deleteProductImage(productId: String)

    fun readMeasuringUnits(): Flow<List<MeasuringUnit>>

    suspend fun insertMeasuringUnits(measuringUnit: MeasuringUnit)


    fun readCategory(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    fun readCurrency(): Flow<List<Currency>>

    suspend fun insertCurrency(currency: Currency)

    suspend fun deleteCurrency()

    fun readSubscription(): Flow<List<Subscription>>

    suspend fun insertSubscription(subscription: Subscription)

    suspend fun deleteSubscription()

}

class OfflineEaglewayRepository(private val dao: ProductDao, private val subDao: SubscriptionDao): EaglewayRepository {

    override fun getImagePreview(): Flow<List<ProductPreview>> = dao.read()

    override suspend fun insertImagePreview(productPreview: ProductPreview) = dao.create(productPreview)

    override suspend fun updateImagePreview(productPreview: ProductPreview) = dao.update(productPreview)

    override suspend fun updateImagePreview(id: Long, productPreview: ProductPreview)  = dao.update(id, productPreview.bitmap, productPreview.isClickable)

    override suspend fun deleteImagePreview(productPreview: List<ProductPreview>) = dao.delete(productPreview)

    override fun getFilterData(): Flow<ProductPreview> = dao.readFilter()

    override suspend fun deleteAll() = dao.deleteAll()

    override suspend fun deleteImagePreview(productPreview: ProductPreview) = dao.delete(productPreview)

    override suspend fun insertProductData(productInfo: ProductInfo) = dao.create(productInfo)

    override suspend fun insertProductImage(productImage: ProductImage) = dao.create(productImage)

    override fun getProductWithImages(): Flow<List<ProductInfoWithImages>> = dao.getProductWithImages()

    override fun getProductWithImages(id: Int): Flow<ProductInfoWithImages> = dao.getProductWithImages(id)

    override suspend fun updateProductData(id: Int, productName: String, salesPrice: Int, mrp: Int, quantity: Int, description: String) = dao.updateProductData(id, productName, salesPrice, mrp, quantity, description)

    override suspend fun deleteProductData() = dao.deleteProductData()

    override suspend fun deleteProductImage() = dao.deleteProductImage()

    override suspend fun deleteProductData(productId: String) = dao.deleteProductData(productId)

    override suspend fun deleteProductImage(productId: String) = dao.deleteProductImage(productId)

    override fun readMeasuringUnits(): Flow<List<MeasuringUnit>> = dao.readMeasuringUnits()

    override suspend fun insertMeasuringUnits(measuringUnit: MeasuringUnit) = dao.insertMeasuringUnits(measuringUnit)

    override fun readCategory(): Flow<List<Category>> = dao.readCategory()

    override suspend fun insertCategory(category: Category) = dao.insertCategory(category)

    override suspend fun deleteCategory(category: Category) = dao.deleteCategory(category)

    override fun readCurrency(): Flow<List<Currency>> = dao.readCurrency()

    override suspend fun insertCurrency(currency: Currency) = dao.insertCurrency(currency)

    override suspend fun deleteCurrency() = dao.deleteCurrency()

    override fun readSubscription(): Flow<List<Subscription>> = subDao.read()

    override suspend fun insertSubscription(subscription: Subscription) = subDao.insert(subscription)

    override suspend fun deleteSubscription() = subDao.deleteSubscriptions()
}

