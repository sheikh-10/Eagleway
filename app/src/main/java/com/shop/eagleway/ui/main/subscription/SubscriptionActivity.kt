package com.shop.eagleway.ui.main.subscription

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.Wallet.WalletOptions
import com.google.android.gms.wallet.WalletConstants
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.utility.log
import com.shop.eagleway.utility.toast
import com.shop.eagleway.viewmodel.SubscriptionViewModel
import org.json.JSONArray
import org.json.JSONObject

class SubscriptionActivity : ComponentActivity() {
    companion object {
        private const val TAG = "SubscriptionActivity"
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, SubscriptionActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                SubscriptionScreen()
            }
        }
    }
}