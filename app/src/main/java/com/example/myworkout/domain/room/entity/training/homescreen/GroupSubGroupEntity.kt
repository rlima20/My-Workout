package com.example.myworkout.domain.room.entity.training.homescreen

import androidx.room.Entity

// Esta tabela fará a junção entre MuscleGroup e MuscleSubGroup.
@Entity(
    tableName = "group_sub_group",
    primaryKeys = ["muscleGroupId", "muscleSubGroupId"]
)
data class GroupSubGroupEntity(
    val muscleGroupId: Int,
    val muscleSubGroupId: Int,
    val selected: Boolean = false
)