package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoalWithMacrosModel

interface GetCalorieGoalWithMacrosUseCase {
    suspend fun getCalorieGoalWithMacros(): List<CalorieGoalWithMacrosModel>
}