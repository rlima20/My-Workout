package com.example.myworkout

import androidx.compose.runtime.Composable
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.BodyPart
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

    val muscleGroups = mutableListOf(
        MuscleGroupModel(
            muscleGroupId = 1,
            name = "Peito e Ombro",
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 2,
            name = "Ombro",
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 3,
            name = "Braço",
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 4,
            name = "Pernas",
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 5,
            name = "Abdômen",
            image = BodyPart.OTHER
        ),
    )

    val muscleSubGroups = mutableListOf(
        MuscleSubGroupModel(
            id = 0,
            name = "Superior",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 1,
            name = "Lateral",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 2,
            name = "Posterior",
            selected = false
        )
    )
}