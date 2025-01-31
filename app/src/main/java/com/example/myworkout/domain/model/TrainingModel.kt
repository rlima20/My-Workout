package com.example.myworkout.domain.model

import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status


data class TrainingModel(
    val trainingId: Int,
    val status: Status,
    val dayOfWeek: DayOfWeek,
    val trainingName: String
)