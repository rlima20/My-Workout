package com.example.myworkout.presentation.viewmodel.viewstate

import com.example.myworkout.domain.model.TrainingModel

sealed class TrainingViewState {
    object Empty : TrainingViewState()
    object Error : TrainingViewState()
    object Loading : TrainingViewState()
    data class Success(val trainings: List<TrainingModel>) : TrainingViewState()
}