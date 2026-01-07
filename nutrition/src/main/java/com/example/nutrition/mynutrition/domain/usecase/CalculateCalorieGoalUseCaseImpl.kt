package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.enums.ActivityLevel
import com.example.nutrition.mynutrition.domain.model.enums.CalorieGoalType

class CalculateCalorieGoalUseCaseImpl: CalculateCalorieGoalUseCase {
    override fun calculateCalorieGoal(tmb: Int, activity: ActivityLevel, goal: CalorieGoalType): Int {
        val maintenance = (tmb * activity.factor).toInt()
        return when (goal) {
            CalorieGoalType.GAIN -> maintenance + 500
            CalorieGoalType.MAINTAIN -> maintenance
            CalorieGoalType.LOSE -> maintenance - 500
        }
    }
}