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

    @Query("SELECT * FROM product_data ORDER BY id DESC")
    fun getProductWithImages(): Flow<List<ProductInfoWithImages>>

    @Query("SELECT * FROM product_data WHERE id = :id")
    fun getProductWithImages(id: Int): Flow<ProductInfoWithImages>

    @Query("UPDATE product_data SET product_name = :productName, sales_price = :salesPrice, mrp = :mrp, quantity = :quantity, description = :description WHERE id = :id")
    suspend fun updateProductData(id: Int, productName: String, salesPrice: Int, mrp: Int, quantity: Int, description: String)

    @Query("DELETE FROM product_data WHERE id >= 0")
    suspend fun deleteProductData()

    @Query("DELETE FROM product_image WHERE id >= 0")
    suspend fun deleteProductImage()

    @Query("DELETE FROM product_data WHERE product_id = :productId")
    suspend fun deleteProductData(productId: String)

    @Query("DELETE FROM product_image WHERE product_id = :productId")
    suspend fun deleteProductImage(productId: String)

    @Query("SELECT * FROM measuring_unit ORDER BY unit_key ASC")
    fun readMeasuringUnits(): Flow<List<MeasuringUnit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasuringUnits(measuringUnit: MeasuringUnit)

    @Query("SELECT * FROM category ORDER BY category ASC")
    fun readCategory(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM currency ORDER BY currency_key ASC")
    fun readCurrency(): Flow<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)

    @Query("Delete FROM Currency WHERE id >= 0")
    suspend fun deleteCurrency()
}