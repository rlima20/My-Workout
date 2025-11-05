package com.example.myworkout.presentation.viewmodel.viewstate

sealed class MuscleGroupViewState {
    object Loading : MuscleGroupViewState()
    object Error : MuscleGroupViewState()
    object Success : MuscleGroupViewState()
    object SuccessDeleteGroup : MuscleGroupViewState()
    object DatabaseCreated : MuscleGroupViewState()
}