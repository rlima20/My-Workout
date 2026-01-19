package com.example.nutrition.mynutrition.domain.model.calorie

import com.example.nutrition.mynutrition.domain.model.macro.MacroResult

data class CalorieGoal(
    val calorieGoalId: Int = 0,
    val tmb: Int = 0,
    val calorieGoal: Int = 0,
    val macrosId: Int? = null,
)
