package com.shop.eagleway.data

import android.graphics.Bitmap
import android.text.style.ClickableSpan
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(preview: ProductPreview): Long

    @Query("SELECT * FROM product_preview ORDER BY id DESC ")
    fun read(): Flow<List<ProductPreview>>

    @Update
    suspend fun update(preview: ProductPreview)

    @Query("UPDATE product_preview SET bitmap = :bitmap, is_clickable = :isClickable WHERE id = :id")
    suspend fun update(id: Long, bitmap: Bitmap?, isClickable: Boolean)

    @Delete
    suspend fun delete(preview: List<ProductPreview>)

    @Delete
    suspend fun delete(preview: ProductPreview)

    @Query("SELECT * FROM product_preview WHERE is_clickable = true")
    fun readFilter(): Flow<ProductPreview>

    @Query("SELECT COUNT(*) FROM product_preview")
    fun isEmpty(): Int

    @Query("DELETE FROM product_preview WHERE id >= 0")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(productInfo: ProductInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(productImage: ProductImage)

    @Query("SELECT * FROM product_data")
    fun getProductWithImages(): Flow<List<ProductInfoWithImages>>

    @Query("DELETE FROM product_data WHERE id >= 0")
    suspend fun deleteProductData()
}