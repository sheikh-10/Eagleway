package com.shop.eagleway.ui.main.product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.coroutineScope
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

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
                viewModel.updateNameField(it.product?.productInfo?.productName.toString())
                viewModel.updateSalesPriceField(it.product?.productInfo?.salesPrice ?: 0)
                viewModel.updateMrpField(it.product?.productInfo?.mrp ?: 0)
                viewModel.updateQuantityField(it.product?.productInfo?.quantity ?: 0)
                viewModel.updateDescriptionField(it.product?.productInfo?.description.toString())
            }
        }
    }
}