package com.example.nutrition.mynutrition.presentation.core.calorie.viewmodel.viewmodelfake

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType
import com.example.nutrition.mynutrition.domain.usecase.CalculateCalorieGoalUseCase

class CalculateCalorieGoalUseCaseFake() : CalculateCalorieGoalUseCase {
    override fun calculateCalorieGoal(
        tmb: Int,
        activityLevel: ActivityLevel,
        goalType: CalorieGoalType
    ): Int {
        TODO("Not yet implemented")
    }

}