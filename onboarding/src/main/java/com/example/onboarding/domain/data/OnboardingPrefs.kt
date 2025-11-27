package com.example.onboarding.domain.data

import android.content.Context
import androidx.core.content.edit

class OnboardingPrefs(private val context: Context) {

    fun setCompleted(value: Boolean) {
        val prefs = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        prefs.edit { putBoolean(KEY_COMPLETED, value) }
    }

    fun isCompleted(): Boolean {
        val prefs = context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean(KEY_COMPLETED, false)
    }

    companion object Constants{
        private const val PREF_NAME = "onboarding_prefs"
        private const val KEY_COMPLETED = "onboarding_completed"
    }
}