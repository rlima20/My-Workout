package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoalWithMacrosModel
import com.example.nutrition.mynutrition.domain.repository.CalorieGoalRepository

class GetCalorieGoalWithMacrosUseCaseImpl(private val calorieGoalRepository: CalorieGoalRepository) :
    GetCalorieGoalWithMacrosUseCase {
    override suspend fun getCalorieGoalWithMacros(): List<CalorieGoalWithMacrosModel> {
        return calorieGoalRepository.getCalorieGoalWithMacros()
    }
}