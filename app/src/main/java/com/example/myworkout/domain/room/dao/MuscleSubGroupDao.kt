package com.example.myworkout.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity

@Dao
interface MuscleSubGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(muscleSubGroup: MuscleSubGroupEntity)

    @Query("SELECT * FROM muscle_sub_group")
    fun getAllMuscleSubGroups(): List<MuscleSubGroupEntity>

    @Query("SELECT * FROM muscle_sub_group WHERE muscleSubGroupId = :id")
    fun getMuscleSubGroupById(id: Int): MuscleSubGroupEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSubGroup(subGroup: MuscleSubGroupEntity)
}