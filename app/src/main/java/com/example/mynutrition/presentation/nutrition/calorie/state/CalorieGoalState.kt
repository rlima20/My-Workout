package com.example.mynutrition.presentation.nutrition.calorie.state

import com.example.mynutrition.domain.model.MacroResult
import com.example.mynutrition.domain.model.enums.CalorieGoalType

data class CalorieGoalState(
    val goalType: CalorieGoalType = CalorieGoalType.MAINTAIN,
    val tmb: Int = 0,
    val calorieGoal: Int = 0,
    val macros: MacroResult? = null,
    val isLoading: Boolean = true,
    val error: String? = ""
)