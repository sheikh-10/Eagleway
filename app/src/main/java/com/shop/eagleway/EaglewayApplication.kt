package com.shop.eagleway

import android.app.Application
import com.shop.eagleway.container.AppContainer
import com.shop.eagleway.container.EaglewayAppContainer
import com.shop.eagleway.data.EaglewayDatabase

class EaglewayApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        val context = EaglewayDatabase.getDatabase(applicationContext)
        container = EaglewayAppContainer(context.productDao(), context.subDao())
    }
}