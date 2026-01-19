package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity

interface SaveCalorieGoalUseCase {
    suspend fun saveCalorieGoal(
        tmb: Int,
        calorieGoal: Int,
        macros: MacroResultEntity
    )
}