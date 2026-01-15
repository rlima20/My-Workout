package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType

class CalculateCalorieGoalUseCaseImpl : CalculateCalorieGoalUseCase {
    override fun calculateCalorieGoal(
        tmb: Int,
        activityLevel: ActivityLevel,
        goalType: CalorieGoalType
    ): Int {
        val maintenance = (tmb * activityLevel.factor).toInt()
        return when (goalType) {
            CalorieGoalType.GAIN -> maintenance + 500
            CalorieGoalType.MAINTAIN -> maintenance
            CalorieGoalType.LOSE -> maintenance - 500
        }
    }
}