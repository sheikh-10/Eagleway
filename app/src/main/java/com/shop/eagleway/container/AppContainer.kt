package com.shop.eagleway.container

import com.shop.eagleway.data.EaglewayRepository
import com.shop.eagleway.data.OfflineEaglewayRepository
import com.shop.eagleway.data.ProductDao
import com.shop.eagleway.data.SubscriptionDao

interface AppContainer {
    val eaglewayRepository: EaglewayRepository
}

class EaglewayAppContainer(private val dao: ProductDao, private val subDao: SubscriptionDao): AppContainer {
    override val eaglewayRepository: EaglewayRepository by lazy {
        OfflineEaglewayRepository(dao, subDao)
    }
}