package com.example.myworkout.presentation.viewmodel

import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.TrainingModel


sealed class TrainingViewAction{
    object FetchTrainings: TrainingViewAction()
    object CreateTrainings: TrainingViewAction()
}

sealed class TrainingViewState {
    object InitialState: TrainingViewState()
    object Empty : TrainingViewState()
    object ErrorMessage : TrainingViewState()
    object Loading : TrainingViewState()
    data class Success(val trainingData: List<TrainingModel>) : TrainingViewState()
}

sealed class MuscleGroupViewState {
    object Success: MuscleGroupViewState()
    object ErrorMessage : MuscleGroupViewState()
    object Loading : MuscleGroupViewState()
}