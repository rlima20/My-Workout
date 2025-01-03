package com.example.myworkout

import android.app.Application
import com.example.myworkout.di.AppModule

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.provideDatabase(this@AppApplication)
    }
}