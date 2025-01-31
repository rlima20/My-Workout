package com.example.myworkout.domain.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_sub_group")
data class MuscleSubGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val muscleSubGroupId: Int = 0,
    val name: String
)