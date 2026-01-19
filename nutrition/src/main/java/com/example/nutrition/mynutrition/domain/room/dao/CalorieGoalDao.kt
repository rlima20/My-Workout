package com.example.nutrition.mynutrition.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nutrition.mynutrition.domain.room.entity.CalorieGoalEntity
import com.example.nutrition.mynutrition.domain.room.entity.CalorieGoalWithMacros

@Dao
interface CalorieGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(calorieGoalEntity: CalorieGoalEntity)

    @Transaction
    @Query("SELECT * FROM calorie_goal")
    suspend fun getCalorieGoalWithMacros(): List<CalorieGoalWithMacros>
}