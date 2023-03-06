package com.shop.eagleway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shop.eagleway.ui.theme.EaglewayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                EaglewayApp()
            }
        }
    }
}
