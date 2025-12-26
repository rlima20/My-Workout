package com.example.mynutrition.domain.model

data class NutritionResult(
    val tmb: Int,
    val maintenanceCalories: Int,
    val calorieGoal: Int,
    val macros: MacroResult
)