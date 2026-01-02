package com.example.nutrition.mynutrition.presentation.calorie.props

import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.presentation.calorie.state.CalorieGoalState

data class CalorieGoalProps(
    val state: CalorieGoalState,
    val onGoalChanged: (CalorieGoalType) -> Unit
)