package com.shop.eagleway.ui.registration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.shop.eagleway.ui.main.HomeActivity
import com.shop.eagleway.ui.theme.EaglewayTheme

class RegistrationActivity: ComponentActivity() {

    private val auth = FirebaseAuth.getInstance()

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, RegistrationActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                RegisterApp(activity = this@RegistrationActivity)
            }
        }

        if (auth.currentUser != null) {
            this.finish()
            HomeActivity.startActivity(this@RegistrationActivity)
        }
    }
}
