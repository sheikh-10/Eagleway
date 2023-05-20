package com.shop.eagleway.container

import com.shop.eagleway.data.EaglewayRepository
import com.shop.eagleway.data.OfflineEaglewayRepository
import com.shop.eagleway.data.ProductDao

interface AppContainer {
    val eaglewayRepository: EaglewayRepository
}

class EaglewayAppContainer(private val dao: ProductDao): AppContainer {
    override val eaglewayRepository: EaglewayRepository by lazy {
        OfflineEaglewayRepository(dao)
    }
}