package com.example.myworkout.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.model.TrainingMuscleGroupModel
import com.example.myworkout.domain.usecase.training.TrainingUseCase
import com.example.myworkout.enums.Status
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TrainingViewModel(
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<TrainingViewState> =
        MutableStateFlow(TrainingViewState.Empty)
    val viewState: StateFlow<TrainingViewState> get() = _viewState

    private val _trainings: MutableStateFlow<List<TrainingModel>> = MutableStateFlow(listOf())
    val trainings: StateFlow<List<TrainingModel>> get() = _trainings

    private val _isHomeScreen = MutableStateFlow(true)
    val isHomeScreen: StateFlow<Boolean> get() = _isHomeScreen

    private val _appBarTitle = MutableStateFlow("Home")
    val appBarTitle: StateFlow<String> get() = _appBarTitle

    fun updateTraining(trainingModel: TrainingModel) {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                trainingUseCase.updateTraining(trainingModel)
                fetchTrainings()
            }catch (_: Exception){
                setErrorState()
            }
        }
    }

    fun setEmptyState() {
        _viewState.value = TrainingViewState.Empty
    }

    fun fetchTrainings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val trainings = trainingUseCase.getTrainings()
                setListOfTrainings(trainings)
            } catch (e: Exception) {
                _viewState.value = TrainingViewState.Error
                Log.e("RAPHAEL", "Erro: $e")
            }
        }
    }

    fun setListOfTrainings(data: List<TrainingModel>) {
        verifyEmptyList(data)
    }

    fun verifyEmptyList(data: List<TrainingModel>) {
        if (data.isEmpty()) {
            _viewState.value = TrainingViewState.Empty
        } else {
            _trainings.value = data
            setSuccessState(data)
        }
    }

    fun insertTraining(training: TrainingModel) {
        viewModelScope.launch {
            setLoadingState()
            try {
                trainingUseCase.insertTraining(training)
                fetchTrainings()
            } catch (e: Exception) {
                setErrorState()
            }
        }
    }

    fun setSuccessState(trainings: List<TrainingModel>) {
        _viewState.value = TrainingViewState.Success(trainings)
        // _trainings.value = trainings
    }

    fun setErrorState() {
        _viewState.value = TrainingViewState.Error
    }

    fun setLoadingState() {
        _viewState.value = TrainingViewState.Loading
        _viewState.value = TrainingViewState.Loading
    }

    fun setIsHomeScreen(value: Boolean) {
        _isHomeScreen.value = value
    }

    fun setAppBarTitle(value: String) {
        _appBarTitle.value = value
    }

    fun clearStatus(trainingId: Int, status: Status) {
        viewModelScope.launch {
            trainingUseCase.clearStatus(trainingId, status)
            fetchTrainings()
        }
    }

    fun insertTrainingMuscleGroup(trainingMuscleGroup: TrainingMuscleGroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            trainingUseCase.insertTrainingMuscleGroup(trainingMuscleGroup)
        }
    }
}