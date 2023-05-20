package com.shop.eagleway

import android.app.Application
import com.shop.eagleway.container.AppContainer
import com.shop.eagleway.container.EaglewayAppContainer
import com.shop.eagleway.data.EaglewayDatabase

class EaglewayApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = EaglewayAppContainer(EaglewayDatabase.getDatabase(applicationContext).productDao())
    }
}