package com.example.myworkout.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.usecase.training.TrainingUseCase
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class TrainingViewModel(
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {

    private val _trainingViewState: MutableStateFlow<TrainingViewState> =
        MutableStateFlow(TrainingViewState.InitialState)
    val trainingViewState: StateFlow<TrainingViewState>
        get() = _trainingViewState

    private val _isHomeScreen = MutableStateFlow(true)
    val isHomeScreen: StateFlow<Boolean>
        get() = _isHomeScreen

    private val _appBarTitle = MutableStateFlow("Home Screen")
    val appBarTitle: StateFlow<String>
        get() = _appBarTitle

    fun setIsHomeScreen(value: Boolean) {
        _isHomeScreen.value = value
    }

    fun setAppBarTitle(value: String) {
        _appBarTitle.value = value
    }

    fun dispatchViewAction(trainingViewAction: TrainingViewAction) {
        when (trainingViewAction) {
            is TrainingViewAction.FetchTrainings -> {
                fetchTrainings()
            }

            is TrainingViewAction.CreateTrainings -> {
                createTrainings()
            }
        }
    }

    /* Essa função será usada somente no desenvolvimento. Após isso, a criação será feita dinamicamente*/
    private fun createTrainings() {
        viewModelScope.launch(Dispatchers.IO) {
            _trainingViewState.value = TrainingViewState.Loading
            try {
                trainingUseCase.insertTraining(
                    TrainingModel(
                        trainingId = 0,
                        status = Status.PENDING,
                        dayOfWeek = DayOfWeek.MONDAY,
                        trainingName = "Peito e Ombro"
                    )
                )
                createTrainingMuscleGroupRelation()
                dispatchViewAction(TrainingViewAction.FetchTrainings)
            } catch (e: Exception) {
                _trainingViewState.value = TrainingViewState.Error
            }
        }
    }

    /* Essa função será usada somente no desenvolvimento. Após isso, a criação será feita dinamicamente*/
    private fun createTrainingMuscleGroupRelation() {
        insertTrainingMuscleGroup(
            TrainingMuscleGroupModel(
                trainingId = 0,
                muscleGroupId = 5
            )
        )
    }

    private fun fetchTrainings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trainings = trainingUseCase.getTrainings()
                setListOfTrainings(trainings)
            } catch (e: Exception) {
                _trainingViewState.value = TrainingViewState.Error
                Log.e("RAPHAEL", "Erro: $e")
            }
        }
    }

    private fun setListOfTrainings(data: List<TrainingModel>) {
        verifyEmptyList(data)
    }

    private fun verifyEmptyList(data: List<TrainingModel>) {
        if (data.isEmpty()) _trainingViewState.value = TrainingViewState.Empty
        else _trainingViewState.value = TrainingViewState.Success(data)
    }

    private fun insertTraining(training: TrainingModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTraining(training)
        }
    }

    private fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTrainingMuscleGroup(trainingMuscleGroup)
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
}