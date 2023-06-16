package com.shop.eagleway.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shop.eagleway.EaglewayApplication
import com.shop.eagleway.data.EaglewayRepository
import com.shop.eagleway.data.Subscription
import com.shop.eagleway.utility.PaymentUtility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SubscriptionUiState(val subscription: List<Subscription> = emptyList())

class SubscriptionViewModel(private val repository: EaglewayRepository): ViewModel() {

    private val database: FirebaseDatabase = Firebase.database

    private var _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    fun onPlanClick(plan: List<Subscription.SubscriptionPlan>, subIndex: Int) {
        _uiState.update {
            SubscriptionUiState(
                it.subscription.mapIndexed { index, subscription ->
                    subscription.copy(plan = if (subIndex == index) plan else subscription.plan)
                }
            )
        }
    }

    fun getSubscriptionData() = viewModelScope.launch {
        val result = repository.readSubscription()
        _uiState.value = SubscriptionUiState(result)
    }

    private fun refreshSubscriptionData() {
        val ref = database.reference.child("subscription")

        ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    it.getValue(com.shop.eagleway.response.Subscription::class.java)?.apply {
                        viewModelScope.launch {
                            repository.insertSubscription(
                                Subscription(
                                    name = name,
                                    plan = plan?.map {
                                            plan -> Subscription.SubscriptionPlan(
                                        id = plan.id,
                                        name = plan.name,
                                        price = plan.price,
                                        description = plan.description,
                                        isSelected = plan.isSelected
                                        ) },
                                    planDetail = planDetail?.map { planDetail ->
                                        Subscription.SubscriptionDetail(
                                            title = planDetail.title,
                                            description = planDetail.description
                                        ) }
                                ))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {  }
        })
    }

    fun requestPayment() {
        PaymentUtility.getPaymentDataRequest("1")
    }

    init {
        refreshSubscriptionData()
        getSubscriptionData()
    }

    companion object {
        private const val TAG = "SubscriptionViewModel"

        private const val TIMEOUT_MILLIS = 5_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EaglewayApplication)

                SubscriptionViewModel(application.container.eaglewayRepository)
            }
        }
    }
}