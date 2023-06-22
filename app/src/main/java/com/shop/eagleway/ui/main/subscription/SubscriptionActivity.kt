package com.shop.eagleway.ui.main.subscription

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.Wallet.WalletOptions
import com.google.android.gms.wallet.WalletConstants
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.utility.log
import com.shop.eagleway.utility.toast
import com.shop.eagleway.viewmodel.HomeViewModel
import com.shop.eagleway.viewmodel.SubscriptionViewModel
import org.json.JSONArray
import org.json.JSONObject

class SubscriptionActivity : ComponentActivity(), PaymentResultListener {
    companion object {
        private const val TAG = "SubscriptionActivity"
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, SubscriptionActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EaglewayTheme {
                SubscriptionScreen()
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d(TAG, "RazorPay Payment successful! $p0")
//        viewModel.updateSubscribeState(true)
//        viewModel.readUserInfoFromDatabase(this@SubscriptionActivity)
//        viewModel.updateUserInfoToDatabase()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.e(TAG, "RazorPay Payment failed! $p0 $p1")
    }

}