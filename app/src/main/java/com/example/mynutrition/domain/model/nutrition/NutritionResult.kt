package com.example.mynutrition.domain.model.nutrition

import com.example.mynutrition.domain.model.macro.MacroResult

data class NutritionResult(
    val tmb: Int,
    val maintenanceCalories: Int,
    val calorieGoal: Int,
    val macros: MacroResult
)