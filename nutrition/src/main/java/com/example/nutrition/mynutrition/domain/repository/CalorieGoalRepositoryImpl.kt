package com.example.nutrition.mynutrition.domain.repository

import com.example.nutrition.mynutrition.domain.mappers.toEntity
import com.example.nutrition.mynutrition.domain.mappers.toModel
import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoal
import com.example.nutrition.mynutrition.domain.model.calorie.CalorieGoalWithMacrosModel
import com.example.nutrition.mynutrition.domain.room.dao.CalorieGoalDao

class CalorieGoalRepositoryImpl(
    private val calorieGoalDao: CalorieGoalDao
) : CalorieGoalRepository {
    override fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        calorieGoalDao.insert(calorieGoal.toEntity())
    }

    override suspend fun getCalorieGoalWithMacros(): List<CalorieGoalWithMacrosModel> {
        return calorieGoalDao.getCalorieGoalWithMacros().toModel()
    }
}