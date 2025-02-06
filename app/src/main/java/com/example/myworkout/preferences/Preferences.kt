package com.example.myworkout.preferences

import android.content.Context
import android.content.SharedPreferences

fun isFirstInstall(context: Context): Boolean {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "my_preferences", Context.MODE_PRIVATE
    )

    return sharedPreferences.getBoolean("is_first_install", true)
}

fun setFirstInstallValue(context: Context, value: Boolean) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(
            "my_preferences",
            Context.MODE_PRIVATE
        )
    sharedPreferences.edit().putBoolean("is_first_install", value).apply()
}
