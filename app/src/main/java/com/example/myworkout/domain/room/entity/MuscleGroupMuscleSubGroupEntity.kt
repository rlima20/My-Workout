package com.example.myworkout.domain.room.entity

import androidx.room.Entity

// Esta tabela fará a junção entre MuscleGroup e MuscleSubGroup.
@Entity(
    tableName = "muscle_group_muscle_sub_group",
    primaryKeys = ["muscleGroupId", "muscleSubGroupId"]
)
data class MuscleGroupMuscleSubGroupEntity(
    val muscleGroupId: Int,
    val muscleSubGroupId: Int
)