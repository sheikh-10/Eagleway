package com.shop.eagleway.ui.main.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shop.eagleway.ui.theme.EaglewayTheme

class UpgradeActivity : ComponentActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, UpgradeActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                UpgradeApp()
            }
        }
    }
}