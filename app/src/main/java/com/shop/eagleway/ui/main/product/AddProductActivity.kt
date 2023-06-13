package com.shop.eagleway.ui.main.product

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {

    val viewModel: ProductViewModel by viewModels(factoryProducer = { ProductViewModel.Factory })

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, AddProductActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                AddProductScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}