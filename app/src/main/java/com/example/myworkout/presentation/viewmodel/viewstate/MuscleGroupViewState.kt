package com.example.myworkout.presentation.viewmodel.viewstate

sealed class MuscleGroupViewState {
    object InitialState : MuscleGroupViewState()
    object SuccessDatabaseCreated : MuscleGroupViewState()
    object SuccessInsertMuscleGroup : MuscleGroupViewState()
    object SuccessInsertMuscleSubGroup : MuscleGroupViewState()
    object SuccessFetchMuscleGroups : MuscleGroupViewState()
    object SuccessFetchMuscleSubGroups : MuscleGroupViewState()
    object SuccessInsertMuscleGroupMuscleSubGroup : MuscleGroupViewState()
    object Error : MuscleGroupViewState()
    object Loading : MuscleGroupViewState()
    object Empty : MuscleGroupViewState()
}