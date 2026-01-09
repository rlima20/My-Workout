package com.example.nutrition.mynutrition.presentation.core.calorie.props

import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.state.CalorieGoalState

data class CalorieGoalProps(
    val state: CalorieGoalState,
    val onGoalChanged: (CalorieGoalType) -> Unit
)

fun getCalorieGoalProps(){}