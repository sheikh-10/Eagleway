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
import java.util.concurrent.TimeUnit

private const val TAG = "RegistrationViewModel"
class RegistrationViewModel: ViewModel() {

    var userPhoneInput by mutableStateOf("")
        private set

    var smsCode by mutableStateOf("")
        private set

    var isSignedInUser by mutableStateOf(false)
        private set

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = Firebase.database
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var verification: String


    fun updatePhoneNumber(phoneNumber: String) {
        userPhoneInput = phoneNumber
    }

    fun updateSmsCodeInput(code: String) {
        smsCode = code
    }

    fun registerPhoneNumber(context: Context, onNextScreen: () -> Unit) {
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
                auth.signOut()
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$userPhoneInput")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(context as Activity)
            .setCallbacks(callback)
            .build()

        if (auth.currentUser != null) {
            Log.d(TAG, "User already signed in")
        } else {
            Log.e(TAG, "User is not signed in")
        }

        checkPhoneNumberFromDatabase {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }


    private fun checkPhoneNumberFromDatabase(verifyNumber: () -> Unit) {
        val ref = database.getReference("users")

        ref.orderByChild("num").equalTo(userPhoneInput).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    Log.i(TAG, "User already signed in")
                    isSignedInUser = true
                    verifyNumber()
                } else {
                    Log.i(TAG, "He is a new user")
                    isSignedInUser = false
                    writeUserEntryToDatabase()
                    verifyNumber()
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    private fun writeUserEntryToDatabase() {
        val ref = database.getReference("users")
        ref.push().setValue(UserPhoneNumber(userPhoneInput))
    }

    fun checkOTP(onNextScreenSignedInUser: () -> Unit, onNextScreenNewUser: () -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verification, smsCode)

        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                if (isSignedInUser) {
                    onNextScreenSignedInUser()
                } else {
                    onNextScreenNewUser()
                }

                Log.d(TAG, "signInWithCredential: Success ${it.result?.user}")
            } else {
                Log.w(TAG, "signInWithCredential: Failure ${it.exception}")
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    Log.e(TAG, "verification code entered was invalid")
                }
            }
        }
    }
}

data class UserPhoneNumber(val num: String?)