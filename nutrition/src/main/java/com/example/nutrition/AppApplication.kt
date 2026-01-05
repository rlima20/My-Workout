package com.example.nutrition

import android.app.Application
import com.example.nutrition.mynutrition.di.nutritionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    companion object {
        lateinit var INSTANCE: AppApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        startKoin {
            androidContext(applicationContext)
            modules(nutritionModule)
        }
    }
}