package com.example.myworkout.domain.room.entity

import androidx.room.Entity

// Esta tabela fará a junção entre Training e MuscleGroup.
@Entity(
    tableName = "training_muscle_group",
    primaryKeys = ["trainingId", "muscleGroupId"]
)
data class TrainingMuscleGroupEntity(
    val trainingId: Int,
    val muscleGroupId: Int
)