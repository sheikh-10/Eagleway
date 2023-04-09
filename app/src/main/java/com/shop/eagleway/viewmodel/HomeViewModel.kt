package com.shop.eagleway.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val TAG = "HomeViewModel"
class HomeViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = Firebase.database

    var userName by mutableStateOf("")
        private set

    var businessName by mutableStateOf("")
        private set

    var userNum by mutableStateOf("")
        private set

    fun updateUserInfo(user: String) { userName = user }

    fun updateBusinessInfo(business: String) { businessName = business }

    fun logout() { auth.signOut() }

    fun readUserInfoFromDatabase(context: Context) {
        val ref = database.getReference("usersInfo")

        context.getSharedPreferences("userNum", Context.MODE_PRIVATE).apply {
            userNum = getString("num", "") ?: ""
        }

        ref.orderByChild("userNum").equalTo(userNum).addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {  data ->
                    data.getValue(User::class.java)?.apply {
                        userInfo?.businessName?.let { businessName = it }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}