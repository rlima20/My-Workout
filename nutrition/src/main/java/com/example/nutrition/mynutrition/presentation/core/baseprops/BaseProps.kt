package com.example.nutrition.mynutrition.presentation.core.baseprops

import androidx.navigation.NavHostController
import com.example.nutrition.mynutrition.preferences.NutritionPrefs

interface BaseProps {
    val navController: NavHostController
    val prefs: NutritionPrefs
}