package com.example.onboarding

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.onboarding.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    companion object {
        lateinit var INSTANCE: AppApplication
            private set
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}