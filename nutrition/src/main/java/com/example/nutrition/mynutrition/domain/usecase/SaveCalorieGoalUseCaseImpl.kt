package com.example.nutrition.mynutrition.domain.usecase

import com.example.nutrition.mynutrition.domain.mappers.toEntity
import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal
import com.example.nutrition.mynutrition.domain.room.dao.CalorieGoalDao

class SaveCalorieGoalUseCaseImpl(private val calorieGoalDao: CalorieGoalDao) :
    SaveCalorieGoalUseCase {
    override fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        calorieGoalDao.insert(calorieGoal.toEntity())
    }
}