package com.shop.eagleway.data

import androidx.lifecycle.LiveData
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

    suspend fun deleteProductData()
}

class OfflineEaglewayRepository(private val dao: ProductDao): EaglewayRepository {

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

    override suspend fun deleteProductData() = dao.deleteProductData()
}

