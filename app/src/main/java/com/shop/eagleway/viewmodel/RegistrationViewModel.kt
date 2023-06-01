package com.shop.eagleway.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shop.eagleway.R
import com.shop.eagleway.request.User
import com.shop.eagleway.request.UserPhoneNumber
import com.shop.eagleway.utility.LoadingState
import java.util.concurrent.TimeUnit

private const val TAG = "RegistrationViewModel"
class RegistrationViewModel: ViewModel(), Registration {

    var userPhoneInput by mutableStateOf("")
        private set

    var userCountryCodeInput by mutableStateOf("91")
        private set

    var smsCode by mutableStateOf("")
        private set

    var isSignedInUser by mutableStateOf(false)
        private set

    var userName by mutableStateOf("")
        private set

    var businessName by mutableStateOf("")
        private set

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = Firebase.database
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var verification: String

    var state by mutableStateOf(LoadingState.False)
        private set

    fun updatePhoneNumber(phoneNumber: String) {
        userPhoneInput = phoneNumber
    }

    fun updateCountryCode(countryCode: String) {
        userCountryCodeInput = countryCode
    }

    fun updateSmsCodeInput(code: String) {
        smsCode = code
    }

    fun updateUserInfo(user: String) { userName = user }

    fun updateBusinessInfo(business: String) { businessName = business }

    fun resetUserInput() {
        userPhoneInput = ""
        smsCode = ""
        isSignedInUser = false
        userName = ""
        businessName = ""
    }

    fun registerPhoneNumber(context: Context, onNextScreen: () -> Unit, isCalledFromSignup: Boolean) {
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "Code: ${credential.smsCode}")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.e(TAG, "verification failed: ${e.message.toString()}")
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                verification = verificationId
                Log.d(TAG, "Code Sent successfully")
                onNextScreen()
                state = LoadingState.False
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+$userCountryCodeInput$userPhoneInput")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context as Activity)
            .setCallbacks(callback)
            .build()

        if (isCalledFromSignup) {
            signup { PhoneAuthProvider.verifyPhoneNumber(options) }
        } else login { PhoneAuthProvider.verifyPhoneNumber(options) }
    }

    private fun writeUserEntryToDatabase() {
        val ref = database.getReference("users")
        ref.push().setValue(UserPhoneNumber(userPhoneInput))
    }

    override fun signup(verifyNumber: () -> Unit) {
        val ref = database.getReference("users")

        state = LoadingState.True

        ref.orderByChild("num").equalTo(userPhoneInput).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    Log.i(TAG, "User already signed in")
                    isSignedInUser = true

                    state = LoadingState.False

                } else {
                    Log.i(TAG, "He is a new user")
                    isSignedInUser = false
                    verifyNumber()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun login(verifyNumber: () -> Unit) {
        val ref = database.getReference("users")

        state = LoadingState.True

        ref.orderByChild("num").equalTo(userPhoneInput).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    Log.i(TAG, "User already signed in")
                    isSignedInUser = false
                    verifyNumber()
                } else {
                    Log.i(TAG, "He is a new user")
                    isSignedInUser = true

                    state = LoadingState.False

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun checkOTPLogin(onNextScreenSignedInUser: () -> Unit, context: Context) {
        val credential = PhoneAuthProvider.getCredential(verification, smsCode)

        state = LoadingState.True

        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                storeNumToLocalDb(context)
                onNextScreenSignedInUser()
                Log.d(TAG, "signInWithCredential: Success ${it.result?.user}")

                state = LoadingState.False

            } else {
                Log.w(TAG, "signInWithCredential: Failure ${it.exception}")
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG, "verification code entered was invalid")
                }
            }
        }
    }

    override fun checkOTPSignup(onNextScreenNewUser: () -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verification, smsCode)

        state = LoadingState.True

        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                onNextScreenNewUser()
                writeUserEntryToDatabase()

                Log.d(TAG, "signInWithCredential: Success ${it.result?.user}")

                state = LoadingState.False

            } else {
                Log.w(TAG, "signInWithCredential: Failure ${it.exception}")
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG, "verification code entered was invalid")
                }
            }
        }
    }

    fun writeUserInfoToDatabase(context: Context) {
        val ref = database.getReference("usersInfo")

        state = LoadingState.True

        storeNumToLocalDb(context)
        ref.push().setValue(User(userPhoneInput,
            User.UserInfo(userName = userName, businessName = businessName)))

        state = LoadingState.False
    }

    private fun storeNumToLocalDb(context: Context) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).apply {
            edit().putString("num", userPhoneInput).apply()
        }
    }
}

interface Registration {
    fun signup(verifyNumber: () -> Unit)
    fun login(verifyNumber: () -> Unit)

    fun checkOTPSignup(onNextScreenNewUser: () -> Unit)
    fun checkOTPLogin(onNextScreenSignedInUser: () -> Unit, context: Context)
}
