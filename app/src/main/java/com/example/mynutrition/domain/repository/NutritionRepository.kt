package com.example.mynutrition.domain.repository

import com.example.mynutrition.domain.model.NutritionResult
import com.example.mynutrition.domain.model.UserInfo
import com.example.mynutrition.domain.model.enums.CalorieGoalType

interface NutritionRepository {
    suspend fun calculateNutrition(info: UserInfo, goal: CalorieGoalType): NutritionResult
}