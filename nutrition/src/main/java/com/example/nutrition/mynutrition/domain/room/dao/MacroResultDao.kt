package com.example.nutrition.mynutrition.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity

@Dao
interface MacroResultDao {

    @Insert
    suspend fun insert(macro: MacroResultEntity): Long
}