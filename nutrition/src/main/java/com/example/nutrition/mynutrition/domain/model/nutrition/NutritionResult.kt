package com.example.nutrition.mynutrition.domain.model.nutrition

import com.example.nutrition.mynutrition.domain.model.macro.MacroResult

data class NutritionResult(
    val tmb: Int,
    val maintenanceCalories: Int,
    val calorieGoal: Int,
    val macros: MacroResult
)