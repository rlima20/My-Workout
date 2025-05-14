package com.example.myworkout.presentation.viewmodel.viewaction

sealed class MuscleGroupViewAction {
    data class CreateMuscleGroup(val name: String) : MuscleGroupViewAction()
    data class CreateMuscleSubGroup(val name: String) : MuscleGroupViewAction()
    data class CreateInitialDatabase(val isFirstInstall: Boolean) : MuscleGroupViewAction()
    object FetchMuscleGroups : MuscleGroupViewAction()
    object FetchMuscleSubGroups : MuscleGroupViewAction()
    object SetupInitialState : MuscleGroupViewAction()
}