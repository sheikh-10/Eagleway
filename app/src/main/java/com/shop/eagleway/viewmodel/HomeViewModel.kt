package com.shop.eagleway.viewmodel

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shop.eagleway.R
import com.shop.eagleway.request.User

private const val TAG = "HomeViewModel"
class HomeViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = Firebase.database

    private var mInterstitialAd: InterstitialAd? = null

    var userName by mutableStateOf("")
        private set

    var businessName by mutableStateOf("")
        private set

    var userNum by mutableStateOf("")
        private set

    var userLanguage by mutableStateOf("")
        private set

    var userEmail by mutableStateOf("")
        private set

    var indexKey by mutableStateOf("")
        private set

    var timeData by mutableStateOf(0L)
        private set

    private var timerValue: Int = 0


    var subscribedUser by mutableStateOf(false)
        private set

    fun updateUserName(user: String) { userName = user }

    fun updateBusinessInfo(business: String) { businessName = business }

    fun updateLanguage(language: String) { userLanguage = language }

    fun updateEmail(email: String) { userEmail = email }

    fun updateSubscribeState(state: Boolean) { subscribedUser = state }

    fun logout() { auth.signOut() }

    fun readUserInfoFromDatabase(context: Context) {
        val ref = database.getReference("usersInfo")

        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).apply {
            userNum = getString("num", "") ?: ""
        }

        ref.orderByChild("userNum").equalTo(userNum).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {  data ->

                    data.key?.let { indexKey = it }

                    data.getValue(User::class.java)?.apply {
                        userInfo?.businessName?.let { businessName = it }
                        userInfo?.userName?.let { userName = it }
                        userInfo?.language?.let { userLanguage = it }
                        userInfo?.email?.let { userEmail = it }
                        userInfo?.subscribedUser?.let { subscribedUser = it }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun updateUserInfoToDatabase() {
        val ref = database.getReference("usersInfo").child(indexKey)

        ref.setValue(User(userNum, User.UserInfo(userName = userName, businessName = businessName, language = userLanguage, email = userEmail, subscribedUser = subscribedUser)))
    }

    fun timer(context: Context) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).apply {
            timerValue = getInt("timer_value", 40)
        }

        val millisInFuture: Long = timerValue.toLong() * 1000

        timeData = millisInFuture

        val countDownTimer =
            object : CountDownTimer(millisInFuture, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeData = millisUntilFinished
                }

                override fun onFinish() {
                    showInterstitial(context) {
                        timer(context)
                    }
                }
            }

        countDownTimer.start()
    }

    fun loadInterstitial(context: Context) {
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917", //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {

        if (mInterstitialAd != null) {

            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                    onAdDismissed()
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitial(context)
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(context as Activity)
        }
    }

    private fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }

    fun getTimerValueFromDb(context: Context) {
        val ref = database.getReference("eaglewayConfig")

        ref.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    storeTimerValueToLocalDb(context, it.getValue(Int::class.java) ?: 40)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun storeTimerValueToLocalDb(context: Context, timerValue: Int) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).apply {
            edit().putInt("timer_value", timerValue).apply()
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeInterstitial()
    }
}