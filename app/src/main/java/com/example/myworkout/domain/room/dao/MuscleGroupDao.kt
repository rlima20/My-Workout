package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myworkout.domain.room.entity.MuscleGroupEntity

@Dao
interface MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(muscleGroup: MuscleGroupEntity)

    @Query("SELECT * FROM muscle_group")
    fun getAllMuscleGroups(): List<MuscleGroupEntity>

    @Query("SELECT * FROM muscle_group WHERE muscleGroupId = :id")
    fun getMuscleGroupById(id: Int): MuscleGroupEntity?
}