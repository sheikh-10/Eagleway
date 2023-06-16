package com.shop.eagleway.ui.main.subscription

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.shop.eagleway.ui.main.product.AddProductActivity
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.utility.PaymentUtility
import com.shop.eagleway.viewmodel.SubscriptionViewModel

class SubscriptionActivity : ComponentActivity() {

    private lateinit var paymentsClient: PaymentsClient

    companion object {
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

//    private fun possiblyShowGooglePayButton() {
//
//        val isReadyToPayJson = PaymentUtility.isReadyToPayRequest() ?: return
//        val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return
//
//        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
//        // OnCompleteListener to be triggered when the result of the call is known.
//        val task = paymentsClient.isReadyToPay(request)
//        task.addOnCompleteListener { completedTask ->
//            try {
//                completedTask.getResult(ApiException::class.java)?.let(::setGooglePayAvailable)
//            } catch (exception: ApiException) {
//                // Process error
//                Log.w("isReadyToPay failed", exception)
//            }
//        }
//    }
}