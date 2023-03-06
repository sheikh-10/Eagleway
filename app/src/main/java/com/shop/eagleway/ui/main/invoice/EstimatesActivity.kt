package com.shop.eagleway.ui.main.invoice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shop.eagleway.ui.main.home.UpgradeActivity
import com.shop.eagleway.ui.theme.EaglewayTheme

class EstimatesActivity : ComponentActivity() {


    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, EstimatesActivity::class.java)
            activity?.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                EstimatesApp()
            }
        }
    }
}