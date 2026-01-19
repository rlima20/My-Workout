package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoalWithMacrosModel
import com.example.nutrition.mynutrition.domain.usecase.GetCalorieGoalWithMacrosUseCase

class GetCalorieGoalWithMacrosUseCaseFake() : GetCalorieGoalWithMacrosUseCase {
    override suspend fun getCalorieGoalWithMacros(): List<CalorieGoalWithMacrosModel> {
        TODO("Not yet implemented")
    }
}