package com.example.myworkout

import androidx.compose.runtime.Composable
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek

class Constants {
    private var idCounter = 0

    @Composable
    fun trainingMock(status: Status) = TrainingModel(
        trainingId = 0,
        status = status,
        dayOfWeek = DayOfWeek.MONDAY,
        trainingName = "Treino A"
    )

    @Composable
    private fun Constants.muscleGroups() = listOf(
        muscleGroupMock(
            muscleGroupName = MUSCLE_GROUP_CHEST,
            muscleSubGroupName = listOf(MUSCLE_GROUP_UPPER_CHEST)
        ),
        muscleGroupMock(
            muscleGroupName = MUSCLE_GROUP_SHOULDER,
            muscleSubGroupName = listOf(
                MUSCLE_GROUP_POST_SHOULDER,
                MUSCLE_GROUP_SIDE_SHOULDER
            )
        )
    )

    @Composable
    private fun muscleGroupMock(
        muscleGroupName: String,
        muscleSubGroupName: List<String>
    ) = MuscleGroupModel(
        muscleGroupId = 0,
        name = muscleGroupName,
        image = com.example.myworkout.enums.BodyPart.ARM
    )

    @Composable
    private fun muscleSubGroupsMock(muscleSubGroupName: List<String>): List<MuscleSubGroupModel> {
        val listOfMuscleSubGroup: MutableList<MuscleSubGroupModel> = mutableListOf()
        muscleSubGroupName.forEach { muscleGroupName ->
            listOfMuscleSubGroup.add(
                MuscleSubGroupModel(
                    id = idCounter,
                    name = muscleGroupName
                )
            )
            idCounter++
        }
        return listOfMuscleSubGroup
    }


    companion object {
        const val MUSCLE_GROUP_CHEST = "Peito"
        const val MUSCLE_GROUP_UPPER_CHEST = "Peito Superior"
        const val MUSCLE_GROUP_SHOULDER = "Ombro"
        const val MUSCLE_GROUP_POST_SHOULDER = "Ombro Superior"
        const val MUSCLE_GROUP_SIDE_SHOULDER = "Ombro Lateral"
    }
}