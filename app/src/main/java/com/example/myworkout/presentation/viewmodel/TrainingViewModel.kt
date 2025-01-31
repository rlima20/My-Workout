package com.example.myworkout.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.usecase.TrainingUseCase
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Timer

@RequiresApi(Build.VERSION_CODES.O)
class TrainingViewModel(
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {
    val viewState: MutableState<TrainingViewState> = mutableStateOf(TrainingViewState())
    val listOfMuscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> = MutableStateFlow(listOf())
    val listOfTrainings: MutableStateFlow<List<TrainingModel>> = MutableStateFlow(listOf())

    fun setupDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            insertTraining(
                TrainingModel(
                    status = Status.PENDING,
                    dayOfWeek = DayOfWeek.MONDAY,
                    trainingName = "Treino A"
                )
            )

            insertMuscleGroup(
                MuscleGroupModel(
                    name = "Ombro"
                )
            )

            insertMuscleSubGroup(
                MuscleSubGroupModel(
                    name = "Posterior"
                )
            )

            insertMuscleSubGroup(
                MuscleSubGroupModel(
                    name = "Anterior"
                )
            )

            insertMuscleSubGroup(
                MuscleSubGroupModel(
                    name = "Lateral"
                )
            )

            insertTrainingMuscleGroup(
                TrainingMuscleGroupModel(
                    trainingId = 1,
                    muscleGroupId = 1
                )
            )

            for (i in 1..3) {
                insertMuscleGroupMuscleSubGroup(
                    MuscleGroupMuscleSubGroupModel(
                        muscleGroupId = 1,
                        muscleSubGroupId = i
                    )
                )
            }
        }
    }

    fun fetchTrainings() {
        viewState.value = TrainingViewState(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trainings = trainingUseCase.getTrainings()
                setListOfTrainings(trainings)
            } catch (e: Exception) {
               Log.e("RAPHAEL", "Erro: $e")
            }
        }
    }

    fun setListOfTrainings(value: List<TrainingModel>){
        listOfTrainings.value = value
    }

    fun fetchTrainings2() {
        viewState.value = TrainingViewState(isLoading = true)

        viewModelScope.launch {
            try {
                val trainings = trainingUseCase.getTrainings()
                viewState.value = TrainingViewState(
                    trainings = trainings,
                    isEmpty = trainings.isEmpty()
                )
            } catch (e: Exception) {
                viewState.value = TrainingViewState(errorMessage = e.message)
            }
        }
    }

    fun getMuscleSubGroupsForTraining(trainingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val value = trainingUseCase.getMuscleSubGroupsForTraining(trainingId)
            updateMuscleSubGroups(value)
        }
    }

    private fun updateMuscleSubGroups(newList: List<MuscleSubGroupModel>) {
        listOfMuscleSubGroups.value = newList
    }

    private fun insertTraining(training: TrainingModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTraining(training)
        }
    }

    private fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertMuscleGroup(muscleGroup)
        }
    }

    private fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertMuscleSubGroup(muscleSubGroup)
        }
    }

    private fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTrainingMuscleGroup(trainingMuscleGroup)
        }
    }

    private fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup)
        }
    }

    sealed class TrainingIntent {
        object FetchTrainings : TrainingIntent()
        data class SaveTraining(val training: TrainingModel) : TrainingIntent()
        data class ClearStatus(val trainingId: Int, val status: Status) : TrainingIntent()
    }

    fun processIntent(intent: TrainingIntent) {
        when (intent) {
            is TrainingIntent.FetchTrainings -> fetchTrainings()
            is TrainingIntent.SaveTraining -> saveTraining(intent.training)
            is TrainingIntent.ClearStatus -> clearStatus(intent.trainingId, intent.status)
        }
    }

    private fun saveTraining(training: TrainingModel) {
        viewModelScope.launch {
            trainingUseCase.insertTraining(training)
            // fetchTrainings()
        }
    }

    private fun clearStatus(trainingId: Int, status: Status) {
        viewModelScope.launch {
            trainingUseCase.clearStatus(trainingId, status)
            // fetchTrainings()
        }
    }

    companion object {
        const val MUSCLE_GROUP_CHEST = "Peito"
        const val MUSCLE_GROUP_UPPER_CHEST = "Peito Superior"
        const val MUSCLE_GROUP_SHOULDER = "Ombro"
        const val MUSCLE_GROUP_POST_SHOULDER = "Ombro Superior"
        const val MUSCLE_GROUP_SIDE_SHOULDER = "Ombro Lateral"
    }
}