package com.example.myworkout.presentation.viewmodel.viewstate

sealed class MuscleGroupViewState {
    object InitialState : MuscleGroupViewState()
    object Success : MuscleGroupViewState()
    object Error : MuscleGroupViewState()
    object Loading : MuscleGroupViewState()
    object Empty : MuscleGroupViewState()
}