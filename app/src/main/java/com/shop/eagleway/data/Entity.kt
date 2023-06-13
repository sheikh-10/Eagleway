package com.shop.eagleway.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.shop.eagleway.utility.smartTruncate
import kotlinx.serialization.Serializable

@Entity(tableName = "product_preview")
data class ProductPreview(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    val bitmap: Bitmap? = null,

    @ColumnInfo(name = "is_clickable")
    val isClickable: Boolean = false
)

@Entity(tableName = "product_data", indices = [Index(value = ["product_name", "sales_price", "mrp", "quantity", "product_id", "description", "category", "currency","measuring_unit"], unique = true)])
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
    val productId: String? = null,

    @ColumnInfo("category")
    val category: String? = null,

    @ColumnInfo("currency")
    val currency: String? = null,

    @ColumnInfo("measuring_unit")
    val measuringUnit: String? = null,

    @ColumnInfo("description")
    val description: String? = null
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

@Entity(tableName = "measuring_unit", indices = [Index(value = ["unit_key", "unit_value"], unique = true)])
data class MeasuringUnit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("unit_key")
    val unitKey: String? = null,

    @ColumnInfo("unit_value")
    val unitValue: String? = null
)

@Entity(tableName = "category", indices = [Index(value = ["category"], unique = true)])
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("category")
    val category: String? = null
)

@Entity(tableName = "currency", indices = [Index(value = ["currency_key", "currency_value", "currency_flag"], unique = true)])
data class Currency(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("currency_key")
    val currencyKey: String? = null,

    @ColumnInfo("currency_value")
    val currencyValue: String? = null,

    @ColumnInfo("currency_flag")
    val currencyFlag: Int? = null
)

@Entity(tableName = "subscription", indices = [Index(value = ["name"], unique = true)])
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("name")
    val name: String? = null,

    @ColumnInfo("plan")
    val plan: List<SubscriptionPlan>? = null,

    @ColumnInfo("plan_detail")
    val planDetail: List<SubscriptionDetail>? = null) {

    data class SubscriptionPlan(val id: Int? = null,
                                val name: String? = null,
                                val price: Int? = null,
                                val description: String? = null,
                                var isSelected: Boolean? = null)

    data class SubscriptionDetail(val title: String? = null, val description: String? = null)
}

data class ProductInfoWithImages(
    @Embedded
    val productInfo: ProductInfo,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "product_id"
    )
    val productImages: List<ProductImage>
)


