package com.example.shoppinglisttask.core.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShoppingListApplication: Application() {
    companion object {
        lateinit var instance: ShoppingListApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
         instance = this
    }
}