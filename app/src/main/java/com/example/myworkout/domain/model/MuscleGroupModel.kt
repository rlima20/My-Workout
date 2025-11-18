package com.example.myworkout.domain.model

data class MuscleGroupModel(
    val muscleGroupId: Int,
    val name: String,
    val selected: Boolean = false,
    val enabled: Boolean = true
)