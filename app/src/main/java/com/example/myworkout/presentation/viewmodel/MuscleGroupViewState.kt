package com.example.myworkout.presentation.viewmodel

sealed class MuscleGroupViewState {
    object InitialState : MuscleGroupViewState()
    object Success : MuscleGroupViewState()
    object Error : MuscleGroupViewState()
    object Loading : MuscleGroupViewState()
}