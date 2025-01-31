package com.example.myworkout.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.domain.mapper.toEntity
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.room.entity.MuscleGroupEntity
import com.example.myworkout.domain.room.entity.MuscleGroupMuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.MuscleSubGroupEntity
import com.example.myworkout.domain.room.entity.TrainingEntity
import com.example.myworkout.domain.room.entity.TrainingMuscleGroupEntity
import com.example.myworkout.domain.usecase.TrainingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TrainingViewModel(
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {

    data class TrainingViewState(
        val trainings: List<TrainingModel> = emptyList(),
        val isEmpty: Boolean = true,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    val listOfTraining: MutableState<List<TrainingModel>> = mutableStateOf(listOf())
    val listOfMuscleSubGroups: MutableState<List<MuscleSubGroupModel>> = mutableStateOf(listOf())

    private val _viewState = MutableLiveData<TrainingViewState>()
    val viewState: LiveData<TrainingViewState> get() = _viewState

    private fun fetchTrainings() {
        _viewState.value = TrainingViewState(isLoading = true)

        viewModelScope.launch {
            try {
                val trainings = trainingUseCase.getTrainings()
                _viewState.value = TrainingViewState(
                    trainings = trainings,
                    isEmpty = trainings.isEmpty()
                )
            } catch (e: Exception) {
                _viewState.value = TrainingViewState(errorMessage = e.message)
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

    fun insertTraining(training: TrainingEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTraining(training)
        }
    }

    fun insertMuscleGroup(muscleGroup: MuscleGroupEntity) {
        /* Todo */
    }

    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupEntity) {
        /* Todo */
    }

    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupEntity) {
        /* Todo */
    }

    fun insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupEntity) {
        /* Todo */
    }

    sealed class TrainingIntent {
        object FetchTrainings : TrainingIntent()
        data class SaveTraining(val training: TrainingModel) : TrainingIntent()
        data class ClearStatus(val trainingId: Int, val status: Status) : TrainingIntent()
    }

    init {
//        processIntent(TrainingIntent.FetchTrainings)
//        saveTraining(
//            TrainingModel(
//                trainingId = 0,
//                status = Status.PENDING,
//                dayOfWeek = java.time.DayOfWeek.SUNDAY,
//            )
//        )
    }

    private fun processIntent(intent: TrainingIntent) {
        when (intent) {
            is TrainingIntent.FetchTrainings -> fetchTrainings()
            is TrainingIntent.SaveTraining -> saveTraining(intent.training)
            is TrainingIntent.ClearStatus -> clearStatus(intent.trainingId, intent.status)
        }
    }

    private fun saveTraining(training: TrainingModel) {
        viewModelScope.launch {
            trainingUseCase.insertTraining(training.toEntity())
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