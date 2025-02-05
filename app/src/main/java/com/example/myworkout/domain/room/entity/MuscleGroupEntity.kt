package com.example.myworkout.domain.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_group")
data class MuscleGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val muscleGroupId: Int = 0,
    val name: String
)
