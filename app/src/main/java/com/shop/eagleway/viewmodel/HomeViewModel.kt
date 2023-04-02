package com.shop.eagleway.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    fun logout() { auth.signOut() }
}