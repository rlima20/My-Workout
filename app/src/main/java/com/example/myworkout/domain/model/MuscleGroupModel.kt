package com.example.myworkout.domain.model

import com.example.myworkout.enums.BodyPart

data class MuscleGroupModel(
    val muscleGroupId: Int,
    val name: String,
    val image: BodyPart,
)

class MuscleGroup(
    val muscleGroupId: Int,
    val name: String,
    val image: BodyPart,
    val selected: Boolean = false,
    val enabled: Boolean = true
)