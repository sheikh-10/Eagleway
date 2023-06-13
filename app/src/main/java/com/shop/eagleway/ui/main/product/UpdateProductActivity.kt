package com.shop.eagleway.ui.main.product

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.coroutineScope
import coil.compose.rememberAsyncImagePainter
import com.shop.eagleway.R
import com.shop.eagleway.model.ImagePreview
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.ProductViewModel
import kotlinx.coroutines.launch
import java.io.File

class UpdateProductActivity : ComponentActivity() {

    val viewModel: ProductViewModel by viewModels(factoryProducer = { ProductViewModel.Factory })

    val id: Int
        get() = intent.getIntExtra("id", 0)

    val productId: String
        get() = intent.getStringExtra("productId") ?: ""

    companion object {
        fun startActivity(activity: Activity?, id: Int?, productId: String?) {
            val intent = Intent(activity, UpdateProductActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("productId", productId)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialData()
        setContent {
            EaglewayTheme {
                UpdateProductScreen(id = id, productId = productId)
            }
        }
    }

    private fun initialData() {
        lifecycle.coroutineScope.launch {
            viewModel.productUiStateSingle(id).collect {

                viewModel.updateImagePreview(this@UpdateProductActivity, it.product?.productImages)

                viewModel.updateNameField(it.product?.productInfo?.productName.toString())
                viewModel.updateSalesPriceField(it.product?.productInfo?.salesPrice ?: 0)
                viewModel.updateMrpField(it.product?.productInfo?.mrp ?: 0)
                viewModel.updateCategoryField(it.product?.productInfo?.category ?: "")
                viewModel.updateCurrencyField(it.product?.productInfo?.currency ?: "")
                viewModel.updateQuantityField(it.product?.productInfo?.quantity ?: 0)
                viewModel.updateMeasuringField(it.product?.productInfo?.measuringUnit ?: "")
                viewModel.updateDescriptionField(it.product?.productInfo?.description.toString())
            }
        }
    }
}