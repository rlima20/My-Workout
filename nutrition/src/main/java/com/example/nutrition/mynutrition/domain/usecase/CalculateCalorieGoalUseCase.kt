package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType

interface CalculateCalorieGoalUseCase {
    fun calculateCalorieGoal(tmb: Int, activity: ActivityLevel, goal: CalorieGoalType): Int
}