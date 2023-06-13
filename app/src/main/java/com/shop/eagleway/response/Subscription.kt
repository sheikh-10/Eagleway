package com.shop.eagleway.response

import kotlinx.serialization.Serializable

@Serializable
data class Subscription(val name: String? = null,
                        val plan: List<SubscriptionPlan>? = null,
                        val planDetail: List<SubscriptionDetail>? = null,
                        ) {

    @Serializable
    data class SubscriptionPlan(val id: Int? = null,
                                val name: String? = null,
                                val price: Int? = null,
                                val description: String? = null,
                                val isSelected: Boolean? = null)

    @Serializable
    data class SubscriptionDetail(val title: String? = null, val description: String? = null)
}
