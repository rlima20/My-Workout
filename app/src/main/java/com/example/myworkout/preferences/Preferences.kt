package com.example.myworkout.preferences

import android.content.Context
import android.content.SharedPreferences

class TrainingPrefs {

    fun isNotFirstInstall(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(SHARED_PREFS_KEY, true)
    }

    fun setFirstInstallValue(context: Context, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        sharedPreferences.edit().putBoolean(SHARED_PREFS_KEY, value).apply()
    }

    companion object {
        const val SHARED_PREFS = "my_preferences"
        const val SHARED_PREFS_KEY = "is_not_first_install"
    }
}