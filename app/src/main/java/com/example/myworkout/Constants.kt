package com.example.myworkout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

class Constants {

    @Composable
    fun getTrainingMock(
        status: Status,
        trainingName: String,
        dayOfWeek: DayOfWeek
    ) = TrainingModel(
        trainingId = 0,
        status = status,
        dayOfWeek = dayOfWeek,
        trainingName = trainingName
    )

    val subGroupsMock = listOf(
        MuscleSubGroupModel(name = "Posterior"),
        MuscleSubGroupModel(name = "Lateral"),
        MuscleSubGroupModel(name = "Anterior"),
        MuscleSubGroupModel(name = "Trapézio"),
    )

    val groupsMock = mutableListOf(
        MuscleGroupModel(
            muscleGroupId = 1,
            name = GROUP_NAME_CHEST,
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 2,
            name = TRAINING_NAME_SHOULDER,
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 3,
            name = GROUP_NAME_ARM,
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 4,
            name = GROUP_NAME_LEGS,
            image = BodyPart.OTHER
        ),
        MuscleGroupModel(
            muscleGroupId = 5,
            name = GROUP_NAME_ABDOMEN,
            image = BodyPart.OTHER
        ),
    )

    val shoulderSubGroupsMock = mutableListOf(
        MuscleSubGroupModel(
            id = 0,
            name = SUB_GROUP_NAME_SHOULDER_ANTERIOR,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 1,
            name = SUB_GROUP_NAME_SHOULDER_LATERAL,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 2,
            name = SUB_GROUP_NAME_SHOULDER_POSTERIOR,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 3,
            name = SUB_GROUP_NAME_SHOULDER_TRAPZ,
            selected = false
        )
    )

    val chestAndTricepsSubGroupsMock = mutableListOf(
        MuscleSubGroupModel(
            id = 4,
            name = SUB_GROUP_NAME_UPPER_CHEST,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 5,
            name = SUB_GROUP_NAME_MEDIAL_CHEST,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 6,
            name = SUB_GROUP_NAME_CROSS,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 7,
            name = SUB_GROUP_NAME_TRICEPS_POLIA,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 8,
            name = SUB_GROUP_NAME_TRICEPS_FRANCH,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 9,
            name = SUB_GROUP_NAME_SHOULDER_LATERAL,
            selected = false
        )
    )

    val bicepsSubGroupsMock = mutableListOf(
        MuscleSubGroupModel(
            id = 10,
            name = SUB_GROUP_NAME_BICEPS_W,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 11,
            name = SUB_GROUP_NAME_BICEPS_HAMMER,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 12,
            name = SUB_GROUP_NAME_BICEPS_SCOTCH,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 13,
            name = SUB_GROUP_NAME_BICEPS_45,
            selected = false
        )
    )

    val backSubGroupsMock = mutableListOf(
        MuscleSubGroupModel(
            id = 14,
            name = SUB_GROUP_NAME_PULLEY,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 15,
            name = SUB_GROUP_NAME_LOWER_BACK,
            selected = false
        ),
        MuscleSubGroupModel(
            id = 16,
            name = SUB_GROUP_NAME_UPPER_BACK,
            selected = false
        ),
    )

    @Composable
    fun getTrainingAndSubGroupsMock() = listOf(
        Pair(
            this.getTrainingMock(
                Status.PENDING,
                TRAINING_NAME_BACK_TRAPS,
                dayOfWeek = DayOfWeek.SUNDAY
            ),
            this.backSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.ACHIEVED,
                TRAINING_NAME_CHEST_TRICEPS,
                dayOfWeek = DayOfWeek.MONDAY
            ),
            this.chestAndTricepsSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.MISSED,
                TRAINING_NAME_ARMS,
                dayOfWeek = DayOfWeek.WEDNESDAY
            ),
            this.bicepsSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.PENDING,
                TRAINING_NAME_BACK_TRAPS,
                dayOfWeek = DayOfWeek.THURSDAY
            ),
            this.backSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.PENDING,
                TRAINING_NAME_SHOULDER,
                dayOfWeek = DayOfWeek.TUESDAY
            ),
            this.shoulderSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.MISSED,
                TRAINING_NAME_ARMS,
                dayOfWeek = DayOfWeek.FRIDAY
            ),
            this.bicepsSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.PENDING,
                TRAINING_NAME_BACK_TRAPS,
                dayOfWeek = DayOfWeek.SATURDAY
            ),
            this.backSubGroupsMock
        ),
    )

    @Composable
    fun getTrainingAndSubGroupsHomeScreenMock() = listOf(
        Pair(
            this.getTrainingMock(
                Status.ACHIEVED,
                TRAINING_NAME_CHEST_TRICEPS,
                dayOfWeek = DayOfWeek.SUNDAY
            ),
            this.chestAndTricepsSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.PENDING,
                TRAINING_NAME_SHOULDER,
                dayOfWeek = DayOfWeek.MONDAY
            ),
            this.shoulderSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.EMPTY, TRAINING_NAME_ARMS,
                dayOfWeek = DayOfWeek.TUESDAY
            ),
            this.bicepsSubGroupsMock
        ),
        Pair(
            this.getTrainingMock(
                Status.MISSED, TRAINING_NAME_ARMS,
                dayOfWeek = DayOfWeek.WEDNESDAY
            ),
            this.bicepsSubGroupsMock
        )
    )

    fun getAllSubGroupsMock(): List<MuscleSubGroupModel> {
        val subGroups: MutableList<MuscleSubGroupModel> = mutableListOf()
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, subGroupName ->
            subGroups.add(
                MuscleSubGroupModel(
                    id = index,
                    name = subGroupName,
                    selected = false
                )
            )
        }
        return subGroups
    }


    fun emptyString(): String = ""

    companion object {
        // Training
        const val TRAINING_NAME_SHOULDER = "Ombro"
        const val TRAINING_NAME_CHEST_TRICEPS = "Peito e Tríceps"
        const val TRAINING_NAME_ARMS = "Bíceps e antebraço"
        const val TRAINING_NAME_BACK_TRAPS = "Costas e trapézio"

        // MuscleGroups
        const val GROUP_NAME_CHEST = "Peito e Ombro"
        const val GROUP_NAME_ARM = "Braço"
        const val GROUP_NAME_LEGS = "Pernas"
        const val GROUP_NAME_ABDOMEN = "Abdômen"

        // MuscleSubGroups
        const val SUB_GROUP_NAME_PULLEY = "Puxada na polia"
        const val SUB_GROUP_NAME_DORSAL_45 = "Dorsal na polia 45C"
        const val SUB_GROUP_NAME_LOWER_BACK = "Remada baixa"
        const val SUB_GROUP_NAME_UPPER_BACK = "Remada alta"
        const val SUB_GROUP_NAME_BICEPS_W = "Bíceps Barra W"
        const val SUB_GROUP_NAME_BICEPS_HAMMER = "Martelo"
        const val SUB_GROUP_NAME_BICEPS_SCOTCH = "Scotch"
        const val SUB_GROUP_NAME_BICEPS_45 = "45 graus"
        const val SUB_GROUP_NAME_UPPER_CHEST = "Supino inclinado"
        const val SUB_GROUP_NAME_MEDIAL_CHEST = "Supino reto"
        const val SUB_GROUP_NAME_CROSS = "Cross"
        const val SUB_GROUP_NAME_TRICEPS_POLIA = "Tríceps polia"
        const val SUB_GROUP_NAME_TRICEPS_FRANCH = "Tríceps Francês"
        const val SUB_GROUP_NAME_TRICEPS_FOREHEAD = "Tríceps Testa"
        const val SUB_GROUP_NAME_SHOULDER_LATERAL = "Ombro lateral"
        const val SUB_GROUP_NAME_SHOULDER_ANTERIOR = "Anterior"
        const val SUB_GROUP_NAME_SHOULDER_POSTERIOR = "Posterior"
        const val SUB_GROUP_NAME_SHOULDER_TRAPZ = "Trapézio"
        const val SUB_GROUP_NAME_FOREARM = "Antebraço"
        const val SUB_GROUP_NAME_FRONT_LEG = "Quadríceps"
        const val SUB_GROUP_NAME_LEG_PRESS = "Leg press"
        const val SUB_GROUP_NAME_STIFF = "Stiff"
        const val SUB_GROUP_NAME_BACK_LEG = "Posterior de perna"
        const val SUB_GROUP_NAME_CALF = "Panturrilha"

        val MUSCLE_SUB_GROUP_NAMES = listOf(
            // Chest
            SUB_GROUP_NAME_UPPER_CHEST,
            SUB_GROUP_NAME_MEDIAL_CHEST,
            SUB_GROUP_NAME_CROSS,

            // Back
            SUB_GROUP_NAME_LOWER_BACK,
            SUB_GROUP_NAME_DORSAL_45,
            SUB_GROUP_NAME_PULLEY,

            // Shoulder
            SUB_GROUP_NAME_SHOULDER_POSTERIOR,
            SUB_GROUP_NAME_SHOULDER_ANTERIOR,
            SUB_GROUP_NAME_SHOULDER_LATERAL,

            // Briceps
            SUB_GROUP_NAME_BICEPS_45,
            SUB_GROUP_NAME_BICEPS_W,
            SUB_GROUP_NAME_BICEPS_SCOTCH,
            SUB_GROUP_NAME_BICEPS_HAMMER,
            SUB_GROUP_NAME_FOREARM,

            // Tríceps
            SUB_GROUP_NAME_TRICEPS_FRANCH,
            SUB_GROUP_NAME_TRICEPS_POLIA,
            SUB_GROUP_NAME_TRICEPS_FOREHEAD,

            // Legs
            SUB_GROUP_NAME_FRONT_LEG,
            SUB_GROUP_NAME_LEG_PRESS,
            SUB_GROUP_NAME_STIFF,
            SUB_GROUP_NAME_BACK_LEG,
            SUB_GROUP_NAME_CALF
        )

        // Layout values
        val DEFAULT_PADDING = 8.dp
        val LAZY_VERTICAL_GRID_SPACING = 16.dp
        val LAZY_VERTICAL_GRID_MIN_SIZE = 140.dp
        val FILTER_CHIP_LIST_PADDING_BOTTOM = 8.dp
        val TRAINING_CARD_PADDING_BOTTOM = 16.dp
        val TRAINING_NAME_MAX_HEIGHT = 30.dp
        val SUB_GROUP_SECTION_BACKGROUND = R.color.button_section_card_color

        // String values
        const val SUNDAY = "DOMINGO"
        const val MONDAY = "SEGUNDA"
        const val TUESDAY = "TERÇA"
        const val WEDNESDAY = "QUARTA"
        const val THURSDAY = "QUINTA"
        const val FRIDAY = "SEXTA"
        const val SATURDAY = "SÁBADO"
    }
}

