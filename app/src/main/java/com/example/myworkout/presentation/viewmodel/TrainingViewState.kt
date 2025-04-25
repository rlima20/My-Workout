package com.example.myworkout.presentation.viewmodel

import com.example.myworkout.domain.model.TrainingModel

sealed class TrainingViewState {
    object InitialState : TrainingViewState()
    object Empty : TrainingViewState()
    object Error : TrainingViewState()
    object Loading : TrainingViewState()
    data class Success(val trainingData: List<TrainingModel>) : TrainingViewState()
}