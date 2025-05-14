package com.example.myworkout

import androidx.compose.runtime.Composable
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

class Constants {

    @Composable
    fun trainingMock(status: Status) = TrainingModel(
        trainingId = 0,
        status = status,
        dayOfWeek = DayOfWeek.MONDAY,
        trainingName = "Treino A"
    )

    val muscleSubGroupNames = listOf(
        "Superior",
        "Dorsal",
        "Inferior",
        "Posterior",
        "Anterior",
        "Bíceps",
        "Antebraço",
        "Tríceps",
        "Lateral",
        "Reto",
        "Medial",
        "Quadríceps",
        "Panturrilhas"
    )
}