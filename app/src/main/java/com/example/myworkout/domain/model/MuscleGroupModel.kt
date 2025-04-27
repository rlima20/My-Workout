package com.example.myworkout.domain.model

import com.example.myworkout.enums.BodyPart

data class MuscleGroupModel(
    val muscleGroupId: Int = 0,
    val name: String,
    val image: BodyPart,
)