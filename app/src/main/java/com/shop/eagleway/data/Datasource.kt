package com.shop.eagleway.data

import com.shop.eagleway.response.Subscription

object Datasource {
    fun subscriptions() = listOf<Subscription>(
        Subscription(
            name = "Basic",
            plan = listOf(
                Subscription.SubscriptionPlan(1, "1 Month", 200, "Use freely for a month", isSelected = true),
                Subscription.SubscriptionPlan(2, "1 Year", 1000, "Use freely for a year", isSelected = false),
                Subscription.SubscriptionPlan(3, "Unlimited", 10000, "Use freely for lifetime", isSelected = false),
            ),
            planDetail = listOf(
                Subscription.SubscriptionDetail(title = "Upto 50 products", description = "Upto 50 products can be added"),
                Subscription.SubscriptionDetail(title = "Ad free", description = "Ad will not be shown")
            )
        ),
        Subscription(
            name = "Advanced",
            plan = listOf(
                Subscription.SubscriptionPlan(4, "1 Month", 300, "Use freely for a month", isSelected = true),
                Subscription.SubscriptionPlan(5, "1 Year", 1500, "Use freely for a year", isSelected = false),
                Subscription.SubscriptionPlan(6, "Unlimited", 10000, "Use freely for lifetime", isSelected = false),
            ),
            planDetail = listOf(
                Subscription.SubscriptionDetail(title = "Upto 100 products", description = "Upto 100 products can be added"),
                Subscription.SubscriptionDetail(title = "Ad free", description = "Ad will not be shown")
            )
        ),
        Subscription(
            name = "Ultimate",
            plan = listOf(
                Subscription.SubscriptionPlan(7, "1 Month", 400, "Use freely for a month", isSelected = true),
                Subscription.SubscriptionPlan(8, "1 Year", 2000, "Use freely for a year", isSelected = false),
                Subscription.SubscriptionPlan(9, "Unlimited", 10000, "Use freely for lifetime", isSelected = false),
            ),
            planDetail = listOf(
                Subscription.SubscriptionDetail(title = "Upto 200 products", description = "Upto 200 products can be added"),
                Subscription.SubscriptionDetail(title = "Ad free", description = "Ad will not be shown")
            )
        ),
    )
}