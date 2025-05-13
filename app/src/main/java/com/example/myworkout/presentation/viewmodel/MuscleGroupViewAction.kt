package com.example.myworkout.presentation.viewmodel

sealed class MuscleGroupViewAction {
    data class CreateMuscleGroup(val name: String): MuscleGroupViewAction()
    data class SetupDatabase(val isFirstInstall: Boolean) : MuscleGroupViewAction()
    object FetchMuscleGroups : MuscleGroupViewAction()
    object FetchMuscleSubGroups : MuscleGroupViewAction()
    object SetupInitialState: MuscleGroupViewAction()
    data class FetchMuscleSubGroupsByTrainingId(val trainingId: Int) : MuscleGroupViewAction()
}