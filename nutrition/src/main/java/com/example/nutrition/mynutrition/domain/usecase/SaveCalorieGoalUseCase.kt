package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal

interface SaveCalorieGoalUseCase {
    fun saveCalorieGoal(calorieGoal: CalorieGoal)
}