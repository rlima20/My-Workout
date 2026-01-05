package com.example.nutrition.mynutrition.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class NutritionPrefs {
    fun getShowNutritionCard(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val showNutritionCard = sharedPreferences.getBoolean(
            SHARED_PREFS_KEY,
            false
        )
        return showNutritionCard
    }

    fun setShowNutritionCard(context: Context, value: Boolean) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        sharedPreferences.edit { putBoolean(SHARED_PREFS_KEY, value) }
    }

    companion object {
        const val SHARED_PREFS = "my_preferences"
        const val SHARED_PREFS_KEY = "should_show_nutrition_card"
    }
}