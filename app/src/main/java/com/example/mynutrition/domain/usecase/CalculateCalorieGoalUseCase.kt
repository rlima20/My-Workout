package com.example.mynutrition.domain.usecase

import com.example.mynutrition.domain.model.enums.ActivityLevel
import com.example.mynutrition.domain.model.enums.CalorieGoalType

class CalculateCalorieGoalUseCase {
    operator fun invoke(tmb: Int, activity: ActivityLevel, goal: CalorieGoalType): Int {
        val maintenance = (tmb * activity.factor).toInt()
        return when (goal) {
            CalorieGoalType.GAIN -> maintenance + 500
            CalorieGoalType.MAINTAIN -> maintenance
            CalorieGoalType.LOSE -> maintenance - 500
        }
    }
}