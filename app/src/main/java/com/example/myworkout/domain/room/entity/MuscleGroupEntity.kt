package com.example.myworkout.domain.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myworkout.enums.BodyPart

@Entity(tableName = "muscle_group")
data class MuscleGroupEntity(
    @PrimaryKey(autoGenerate = false)
    val muscleGroupId: Int = 0,
    val name: String,
    val image: BodyPart,
)
