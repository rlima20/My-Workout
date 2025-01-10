package com.example.myworkout.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

import com.example.myworkout.domain.usecase.TrainingUseCase
import kotlinx.coroutines.launch

class TrainingViewModel(
    private val trainingUseCase: TrainingUseCase
) : ViewModel() {

    sealed class TrainingIntent {
        data object FetchTrainings : TrainingIntent()
        data class SaveTraining(val training: Training) : TrainingIntent()
        data class ClearStatus(val trainingId: Int, val status: Status) : TrainingIntent()
    }

    data class TrainingViewState(
        val trainings: List<Training> = emptyList(),
        val isEmpty: Boolean = true,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    private val _viewState = MutableLiveData<TrainingViewState>()
    val viewState: LiveData<TrainingViewState> get() = _viewState

    init {
        processIntent(TrainingIntent.FetchTrainings)
    }

    private fun processIntent(intent: TrainingIntent) {
        when (intent) {
            is TrainingIntent.FetchTrainings -> fetchTrainings()
            is TrainingIntent.SaveTraining -> saveTraining(intent.training)
            is TrainingIntent.ClearStatus -> clearStatus(intent.trainingId, intent.status)
        }
    }

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

    private fun saveTraining(training: Training) {
        viewModelScope.launch {
            trainingUseCase.saveTraining(training)
            fetchTrainings()
        }
    }

    private fun clearStatus(trainingId: Int, status: Status) {
        viewModelScope.launch {
            trainingUseCase.clearStatus(trainingId, status)
            fetchTrainings()
        }
    }
}
