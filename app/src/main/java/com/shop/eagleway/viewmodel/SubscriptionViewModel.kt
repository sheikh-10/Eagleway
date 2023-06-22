package com.shop.eagleway.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.CheckoutActivity
import com.shop.eagleway.EaglewayApplication
import com.shop.eagleway.data.EaglewayRepository
import com.shop.eagleway.data.Subscription
import com.shop.eagleway.ui.main.subscription.SubscriptionScreen
import com.shop.eagleway.utility.log
import com.shop.eagleway.utility.toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

data class SubscriptionUiState(val subscription: List<Subscription> = emptyList())

class SubscriptionViewModel(
    private val repository: EaglewayRepository,
    private val context: Application
    ): ViewModel() {

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

        viewModelScope.launch {
            uiState.collect {
                it.subscription.toString().log(TAG)
            }
        }
    }

    private fun refreshDb() = viewModelScope.launch {
        repository.deleteSubscription()
    }

    private fun getSubscription() = viewModelScope.launch {
        repository.readSubscription().collect {
            _uiState.value = SubscriptionUiState(
                it.mapIndexed { index, subscription ->
                    subscription.copy(plan = subscription.plan?.mapIndexed { planIndex, plan ->
                        if (planIndex == 0) {
                            plan.copy(isSelected = true)
                        } else plan
                    })
                }
            )
        }
    }

    private fun refreshSubscriptionData() = viewModelScope.launch {
        val ref = database.reference.child("subscription")

        ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                refreshDb()

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

    fun onUpgradeClicked(currentPage: Int, startPayment: (Int) -> Unit) = viewModelScope.launch {
        uiState.collect {
            it.subscription[currentPage].plan?.forEach { plan ->
                if (plan.isSelected == true) {
                    plan.toString().log(TAG)
                    plan.price?.let(startPayment)
                }
            }
        }
    }

    init {
       refreshSubscriptionData()
        getSubscription()
    }

    companion object {
        private const val TAG = "SubscriptionViewModel"

        private const val TIMEOUT_MILLIS = 5_000L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EaglewayApplication)

                SubscriptionViewModel(application.container.eaglewayRepository, application)
            }
        }
    }
}