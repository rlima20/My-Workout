package com.example.mynutrition.presentation.nutrition.calorie.props

import com.example.mynutrition.domain.model.enums.CalorieGoalType
import com.example.mynutrition.presentation.nutrition.calorie.state.CalorieGoalState

data class CalorieGoalProps(
    val state: CalorieGoalState,
    val onGoalChanged: (CalorieGoalType) -> Unit
)