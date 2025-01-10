package com.example.myworkout.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingWithMuscleGroups(
    @Embedded val trainingEntity: TrainingEntity,
    @Relation(
        parentColumn = "trainingId",
        entityColumn = "muscleGroupId"
    )
    val muscleGroups: List<MuscleGroup>
)