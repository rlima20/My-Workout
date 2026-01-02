package com.example.nutrition.mynutrition.presentation.nutrition.calorie.state

import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.model.macro.MacroResult

data class CalorieGoalState(
    val goalType: CalorieGoalType = CalorieGoalType.MAINTAIN,
    val tmb: Int = 0,
    val calorieGoal: Int = 0,
    val macros: MacroResult? = null,
    val isLoading: Boolean = true,
    val error: String? = ""
)