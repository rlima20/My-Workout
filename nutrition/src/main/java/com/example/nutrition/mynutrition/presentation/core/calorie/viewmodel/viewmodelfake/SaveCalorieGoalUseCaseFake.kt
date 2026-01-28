package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal
import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity
import com.example.nutrition.mynutrition.domain.usecase.SaveCalorieGoalUseCase

class SaveCalorieGoalUseCaseFake() : SaveCalorieGoalUseCase {
    override suspend fun saveCalorieGoal(
        id: Int,
        tmb: Int,
        calorieGoal: Int,
        macros: MacroResultEntity
    ) {
        TODO("Not yet implemented")
    }
}