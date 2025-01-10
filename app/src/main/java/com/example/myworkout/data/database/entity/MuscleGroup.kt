package com.example.myworkout.data.database.entity

import androidx.room.PrimaryKey

data class MuscleGroup(
    @PrimaryKey(autoGenerate = true) val muscleGroupId: Int = 0,
    val trainingId: Int,
    val name: String,
)