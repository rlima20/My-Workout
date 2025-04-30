package com.example.myworkout.presentation.viewmodel

sealed class MuscleGroupViewAction {
    data class SetupDatabase(val isFirstInstall: Boolean) : MuscleGroupViewAction()
    object FetchMuscleGroups : MuscleGroupViewAction()
    object FetchMuscleSubGroups : MuscleGroupViewAction()
}