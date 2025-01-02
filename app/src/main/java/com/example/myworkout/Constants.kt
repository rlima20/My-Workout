package com.example.myworkout

import androidx.compose.runtime.Composable
import com.example.myworkout.data.model.MuscleGroup
import com.example.myworkout.data.model.MuscleSubGroup
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

class Constants {
    private var idCounter = 0

    @Composable
    fun trainingMock(status: Status) = Training(
        id = 0,
        muscleGroups = muscleGroups(),
        status = status,
        dayOfWeek = 0,
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
    ) = MuscleGroup(
        id = 0,
        name = muscleGroupName,
        muscleSubGroups = muscleSubGroupsMock(muscleSubGroupName)
    )

    @Composable
    private fun muscleSubGroupsMock(muscleSubGroupName: List<String>): List<MuscleSubGroup> {
        val listOfMuscleSubGroup: MutableList<MuscleSubGroup> = mutableListOf()
        muscleSubGroupName.forEach { muscleGroupName ->
            listOfMuscleSubGroup.add(
                MuscleSubGroup(
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