package com.shop.eagleway.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "product_preview")
data class ProductPreview(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    val bitmap: Bitmap? = null,

    @ColumnInfo(name = "is_clickable")
    val isClickable: Boolean = false
)

@Entity(tableName = "product_data", indices = [Index(value = ["product_name", "sales_price", "mrp", "quantity", "product_id"], unique = true)])
data class ProductInfo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "product_name")
    val productName: String? = null,

    @ColumnInfo("sales_price")
    val salesPrice: Int? = null,

    @ColumnInfo("mrp")
    val mrp: Int? = null,

    @ColumnInfo("quantity")
    val quantity: Int? = null,

    @ColumnInfo("product_id")
    val productId: String? = null
)

@Entity(tableName = "product_image", indices = [Index(value = ["product_id", "image_name"], unique = true)])
data class ProductImage(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("product_id")
    val productId: String? = null,

    @ColumnInfo("image_name")
    val imageName: String? = null

)

data class ProductInfoWithImages(
    @Embedded
    val productInfo: ProductInfo,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "product_id"
    )
    val productImages: List<ProductImage>
)

