package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity

@Dao
interface MuscleGroupMuscleSubGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupEntity)

    @Query("SELECT * FROM muscle_group_muscle_sub_group")
    fun getAllMuscleGroupMuscleSubGroups(): List<MuscleGroupMuscleSubGroupEntity>

    @Query("SELECT * FROM muscle_group_muscle_sub_group WHERE muscleGroupId = :muscleGroupId")
    fun getMuscleSubGroupsForMuscleGroup(muscleGroupId: Int): List<MuscleGroupMuscleSubGroupEntity>
}