package com.shop.eagleway.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this@HomeActivity)

        setContent {
            EaglewayTheme {
                EaglewayApp(activity = this@HomeActivity)
            }

            viewModel.loadInterstitial(this@HomeActivity)
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun BannerAd(modifier: Modifier = Modifier) {

        val context = LocalContext.current

        AndroidView(
            modifier = modifier
                .fillMaxHeight()
                .wrapContentHeight(align = Alignment.CenterVertically),
            factory = {
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-7435434809332811/4565926157"
                    loadAd(AdRequest.Builder().build())
                }
            })
    }


}
