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
    fun trainingMock(status: Status, trainingName: String) = TrainingModel(
        trainingId = 0,
        status = status,
        dayOfWeek = DayOfWeek.MONDAY,
        trainingName = trainingName
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

    val shoulderSubGroups = mutableListOf(
        MuscleSubGroupModel(
            id = 0,
            name = "Anterior",
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
        ),
        MuscleSubGroupModel(
            id = 3,
            name = "Trapézio",
            selected = false
        )
    )

    val chestAndTricepsSubGroups = mutableListOf(
        MuscleSubGroupModel(
            id = 4,
            name = "Supino inclinado",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 5,
            name = "Supino reto",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 6,
            name = "Cross",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 7,
            name = "Tríceps polia",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 8,
            name = "Tríceps Francês",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 9,
            name = "Lateral",
            selected = false
        )
    )

    val bicepsSubGroups = mutableListOf(
        MuscleSubGroupModel(
            id = 10,
            name = "Bíceps Barra W",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 11,
            name = "Martelo",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 12,
            name = "Scotch",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 13,
            name = "45 graus",
            selected = false
        )
    )

    val backSubGroups = mutableListOf(
        MuscleSubGroupModel(
            id = 14,
            name = "Dorsal",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 15,
            name = "Remada baixa",
            selected = false
        ),
        MuscleSubGroupModel(
            id = 16,
            name = "Remada alta",
            selected = false
        ),
    )
}