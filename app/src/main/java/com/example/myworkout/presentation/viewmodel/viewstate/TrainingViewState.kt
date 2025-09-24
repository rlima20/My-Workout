package com.example.myworkout.presentation.viewmodel.viewstate

sealed class TrainingViewState {
    object Empty : TrainingViewState()
    object Error : TrainingViewState()
    object Loading : TrainingViewState()
    object Success : TrainingViewState()
}