package com.shop.eagleway.ui.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shop.eagleway.ui.theme.EaglewayTheme

class RegistrationActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                RegistrationApp(activity = this@RegistrationActivity)
            }
        }
    }
}
