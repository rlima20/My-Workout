package com.example.nutrition.mynutrition.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.nutrition.mynutrition.domain.room.entity.MacroResultEntity

@Dao
interface MacroResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(macro: MacroResultEntity): Long
}