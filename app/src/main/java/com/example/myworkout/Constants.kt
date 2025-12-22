package com.example.myworkout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import com.example.onboarding.domain.model.OnboardingPage

class Constants {

    fun getListOfDays(): List<Pair<DayOfWeek, Boolean>> =
        listOf(
            Pair(DayOfWeek.SATURDAY, false),
            Pair(DayOfWeek.SUNDAY, false),
            Pair(DayOfWeek.THURSDAY, false),
        )

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

    val daysOfWeek = listOf(
        Pair(DayOfWeek.SATURDAY, true),
        Pair(DayOfWeek.FRIDAY, false),
        Pair(DayOfWeek.TUESDAY, true),
        Pair(DayOfWeek.THURSDAY, false),
        Pair(DayOfWeek.SUNDAY, true)
    )

    val subGroupsMock = listOf(
        MuscleSubGroupModel(name = "Posterior"),
        MuscleSubGroupModel(name = "Lateral"),
        MuscleSubGroupModel(name = "Anterior"),
        MuscleSubGroupModel(name = "Trapézio"),
    )

    val newSubGroupsMock = listOf(
        SubGroupModel(name = "Posterior", selected = true),
        SubGroupModel(name = "Lateral"),
        SubGroupModel(name = "Anterior", selected = true),
        SubGroupModel(name = "Trapézio"),
    )

    val groupsMock = mutableListOf(
        MuscleGroupModel(
            muscleGroupId = 1,
            name = GROUP_NAME_CHEST,
        ),
        MuscleGroupModel(
            muscleGroupId = 2,
            name = TRAINING_NAME_SHOULDER,
        ),
        MuscleGroupModel(
            muscleGroupId = 3,
            name = GROUP_NAME_ARM,
        ),
        MuscleGroupModel(
            muscleGroupId = 4,
            name = GROUP_NAME_LEGS,
        ),
        MuscleGroupModel(
            muscleGroupId = 5,
            name = GROUP_NAME_ABDOMEN,
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

    val newChestAndTricepsSubGroupsMock = mutableListOf(
        SubGroupModel(
            id = 4,
            name = SUB_GROUP_NAME_UPPER_CHEST,
            selected = false
        ),
        SubGroupModel(
            id = 5,
            name = SUB_GROUP_NAME_MEDIAL_CHEST,
            selected = false
        ),
        SubGroupModel(
            id = 6,
            name = SUB_GROUP_NAME_CROSS,
            selected = false
        ),
        SubGroupModel(
            id = 7,
            name = SUB_GROUP_NAME_TRICEPS_POLIA,
            selected = false
        ),
        SubGroupModel(
            id = 8,
            name = SUB_GROUP_NAME_TRICEPS_FRANCH,
            selected = false
        ),
        SubGroupModel(
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

    @Composable
    fun getNewTrainingAndSubGroupsHomeScreenMock() = listOf(
        Pair(
            this.getTrainingMock(
                Status.ACHIEVED,
                TRAINING_NAME_CHEST_TRICEPS,
                dayOfWeek = DayOfWeek.SUNDAY
            ),
            this.newChestAndTricepsSubGroupsMock
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

    fun getAllSubGroupsFewMock(): List<MuscleSubGroupModel> {
        val subGroups: MutableList<MuscleSubGroupModel> = mutableListOf()
        MUSCLE_SUB_GROUP_FEW_NAMES.forEachIndexed { index, subGroupName ->
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

    fun getGroupsAndSubgroupsWithRelations(): List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>> =
        listOf(
            mapOf(
                Constants().groupsMock[0] to Constants().chestAndTricepsSubGroupsMock // Peito e Ombro
            ),
            mapOf(
                Constants().groupsMock[1] to Constants().shoulderSubGroupsMock // Ombro
            ),
            mapOf(
                Constants().groupsMock[2] to Constants().bicepsSubGroupsMock // Braço
            ),
            mapOf(
                Constants().groupsMock[3] to Constants().backSubGroupsMock // Costas e trapézio
            ),
            mapOf(
                Constants().groupsMock[4] to Constants().subGroupsMock // Abdômen (mock genérico)
            )
        )

    val pageWelcome = OnboardingPage(
        image = com.example.onboarding.R.drawable.welcome,
        description = "Conheça as funcionalidades incríveis do nosso app."
    )

    val pageYourJourney = OnboardingPage(
        image = com.example.onboarding.R.drawable.home_img,
        title = "Sua Jornada, Seu Ritmo",
        description = "Personalize seus treinos e acompanhe tudo. Tenha total controle sobre sua evolução — um passo de cada vez."
    )

    val pageGroups = OnboardingPage(
        image = com.example.onboarding.R.drawable.create_group_img,
        title = "Organize seus Grupos Musculares",
        description = "Crie grupos musculares para estruturar seus treinos. Assim você mantém tudo organizado e acessa os exercícios de forma rápida e intuitiva."
    )

    val pageYourTraining = OnboardingPage(
        image = com.example.onboarding.R.drawable.new_training_img,
        title = "Monte Seu Treino Ideal",
        description = "Crie grupos musculares para estruturar seus treinos. Assim você mantém tudo organizado e acessa os exercícios de forma rápida e intuitiva."
    )

    val pageEditAndDelete = OnboardingPage(
        image = com.example.onboarding.R.drawable.delete_register_img,
        title = "Edite e Exclua com Gestos Simples",
        description = "Toque e segure para editar qualquer treino ou grupo muscular.\n" +
                "Dê um duplo clique para excluir rapidamente o que não usa mais.\n" +
                "Gerencie tudo de forma prática, direta e do seu jeito."
    )

    val onboardingPages = listOf(
        pageWelcome,
        pageYourJourney,
        pageGroups,
        pageYourTraining,
        pageEditAndDelete
    )


    companion object {
        val dayOrder = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        )

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
        const val SUB_GROUP_NAME_REMADA_SERROTE = "Remada serrote"
        const val SUB_GROUP_NAME_LOWER_BACK = "Remada baixa"
        const val SUB_GROUP_NAME_UPPER_BACK = "Remada alta"
        const val SUB_GROUP_NAME_BICEPS_W = "Bíceps Rosca Barra W"
        const val SUB_GROUP_NAME_BICEPS_HAMMER = "Bíceps Rosca Martelo"
        const val SUB_GROUP_NAME_BICEPS_SCOTCH = "Bíceps Rosca Scotch"
        const val SUB_GROUP_NAME_BICEPS_45 = "45 graus"
        const val SUB_GROUP_NAME_UPPER_CHEST = "Supino inclinado"
        const val SUB_GROUP_NAME_MEDIAL_CHEST = "Supino reto"
        const val SUB_GROUP_NAME_CROSS = "Cross"
        const val SUB_GROUP_NAME_PULLEY_CROSS = "Cross no pulley"
        const val SUB_GROUP_NAME_TRICEPS_POLIA = "Tríceps pulldown na polia"
        const val SUB_GROUP_NAME_TRICEPS_FRANCH = "Tríceps Francês"
        const val SUB_GROUP_NAME_TRICEPS_FOREHEAD = "Tríceps Testa"
        const val SUB_GROUP_NAME_SHOULDER_LATERAL = "Ombro lateral"
        const val SUB_GROUP_NAME_TRAPEZIUS = "Trapézio"
        const val SUB_GROUP_NAME_SHOULDER_ANTERIOR = "Ombro Anterior"
        const val SUB_GROUP_NAME_SHOULDER_POSTERIOR = "Ombro Posterior"
        const val SUB_GROUP_NAME_SHOULDER_TRAPZ = "Trapézio"
        const val SUB_GROUP_NAME_LEG_PRESS = "Perna Leg press"
        const val SUB_GROUP_NAME_BACK_LEG = "Perna Posterior"
        const val SUB_GROUP_NAME_REST = "Descanso"

        val MUSCLE_SUB_GROUP_FEW_NAMES = listOf(
            // Chest
            SUB_GROUP_NAME_UPPER_CHEST,
            SUB_GROUP_NAME_MEDIAL_CHEST,
            SUB_GROUP_NAME_CROSS,
            SUB_GROUP_NAME_PULLEY_CROSS,
        )

        val MUSCLE_SUB_GROUP_NAMES = listOf(
            // Chest
            SUB_GROUP_NAME_UPPER_CHEST,
            SUB_GROUP_NAME_MEDIAL_CHEST,

            // Back
            SUB_GROUP_NAME_LOWER_BACK,
            SUB_GROUP_NAME_REMADA_SERROTE,
            SUB_GROUP_NAME_UPPER_BACK,

            // Shoulder
            SUB_GROUP_NAME_SHOULDER_POSTERIOR,
            SUB_GROUP_NAME_SHOULDER_ANTERIOR,
            SUB_GROUP_NAME_SHOULDER_LATERAL,
            SUB_GROUP_NAME_TRAPEZIUS,

            // Briceps
            SUB_GROUP_NAME_BICEPS_W,
            SUB_GROUP_NAME_BICEPS_SCOTCH,
            SUB_GROUP_NAME_BICEPS_HAMMER,

            // Tríceps
            SUB_GROUP_NAME_TRICEPS_FRANCH,
            SUB_GROUP_NAME_TRICEPS_POLIA,
            SUB_GROUP_NAME_TRICEPS_FOREHEAD,

            // Legs
            SUB_GROUP_NAME_LEG_PRESS,
            SUB_GROUP_NAME_BACK_LEG,

            // Rest
            SUB_GROUP_NAME_REST
        )

        // Layout values
        val DEFAULT_PADDING = 8.dp
        val LAZY_VERTICAL_GRID_SPACING = 16.dp
        val LAZY_VERTICAL_GRID_MIN_SIZE = 140.dp
        val FILTER_CHIP_LIST_PADDING_BOTTOM = 8.dp
        val TRAINING_CARD_PADDING_BOTTOM = 16.dp
        val TRAINING_NAME_MAX_HEIGHT = 30.dp
        val TRAINING_NAME_MAX_HEIGHT_V2 = 50.dp
        val SUB_GROUP_SECTION_BACKGROUND = R.color.white

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

