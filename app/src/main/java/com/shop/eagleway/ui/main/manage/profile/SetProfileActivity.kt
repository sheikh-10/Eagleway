package com.shop.eagleway.ui.main.manage.profile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme

class SetProfileActivity : ComponentActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, SetProfileActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                SetProfileApp()
            }
        }
    }
}