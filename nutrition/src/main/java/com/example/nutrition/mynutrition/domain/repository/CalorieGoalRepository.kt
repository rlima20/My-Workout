package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal
import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoalWithMacrosModel

interface CalorieGoalRepository {
    suspend fun saveCalorieGoal(calorieGoal: CalorieGoal)
    suspend fun getCalorieGoalWithMacros(): List<CalorieGoalWithMacrosModel>
}