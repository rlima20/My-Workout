package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myworkout.domain.room.entity.MuscleGroupEntity

@Dao
interface MuscleGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroup(muscleGroup: MuscleGroupEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGroup(group: MuscleGroupEntity)

    @Delete
    fun deleteGroup(group: MuscleGroupEntity)

    @Query("SELECT * FROM muscle_group")
    fun getAllMuscleGroups(): List<MuscleGroupEntity>

    @Query("SELECT * FROM muscle_group WHERE muscleGroupId = :id")
    fun getMuscleGroupById(id: Int): MuscleGroupEntity?
}