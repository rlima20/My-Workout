package com.example.nutrition.mynutrition.domain.usecase

import androidx.room.Transaction
import com.example.nutrition.mynutrition.domain.room.dao.CalorieGoalDao
import com.example.nutrition.mynutrition.domain.room.dao.MacroResultDao
import com.example.nutrition.mynutrition.domain.room.entity.CalorieGoalEntity
import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity

class SaveCalorieGoalUseCaseImpl(
    private val macroDao: MacroResultDao,
    private val calorieGoalDao: CalorieGoalDao
) :
    SaveCalorieGoalUseCase {

    @Transaction
    override suspend fun saveCalorieGoal(
        tmb: Int,
        calorieGoal: Int,
        macros: MacroResultEntity
    ) {
        val macrosId = macroDao.insert(macros).toInt()

        calorieGoalDao.insert(
            CalorieGoalEntity(
                tmb = tmb,
                calorieGoal = calorieGoal,
                macrosId = macrosId
            )
        )
    }
}