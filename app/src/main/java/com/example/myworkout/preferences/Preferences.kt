package com.example.myworkout.preferences

import android.content.Context
import android.content.SharedPreferences

fun isFirstInstall(context: Context): Boolean {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "my_preferences", Context.MODE_PRIVATE
    )

    val isFirstInstall = sharedPreferences.getBoolean("is_first_install", true)

    // Se for a primeira instalação, atualiza a preferência
    if (isFirstInstall) {
        sharedPreferences.edit().putBoolean("is_first_install", false).apply()
    }

    return isFirstInstall
}