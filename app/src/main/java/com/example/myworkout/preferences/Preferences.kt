package com.example.myworkout.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TrainingPrefs {

    fun getHomeScreenV2(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val isHomeScreenV2 = sharedPreferences.getBoolean(SHARED_PREFS_KEY_IS_V2, false)
        return isHomeScreenV2
    }

    fun setHomeScreenV2(context: Context, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        sharedPreferences.edit { putBoolean(SHARED_PREFS_KEY_IS_V2, value) }
    }

    fun isFirstInstall(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val isFirstInstall = sharedPreferences.getBoolean(SHARED_PREFS_KEY, false)
        return !isFirstInstall
    }

    fun setFirstInstallValue(context: Context, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        sharedPreferences.edit { putBoolean(SHARED_PREFS_KEY, value) }
    }

    companion object {
        const val SHARED_PREFS = "my_preferences"
        const val SHARED_PREFS_KEY = "is_not_first_install"
        const val SHARED_PREFS_KEY_IS_V2 = "is_home_screen_v2"
    }
}