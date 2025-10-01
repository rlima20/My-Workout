//package com.example.myworkout.presentation.viewmodel.viewstate
//
//import com.example.myworkout.domain.model.MuscleGroupModel
//
//sealed class MuscleGroupViewState {
//    object InitialState : MuscleGroupViewState()
//    object SuccessDatabaseCreated : MuscleGroupViewState()
//    object SuccessInsertMuscleGroup : MuscleGroupViewState()
//    object SuccessInsertMuscleSubGroup : MuscleGroupViewState()
//    object SuccessFetchMuscleGroups : MuscleGroupViewState()
//    object SuccessGetSubGroupsByTraining : MuscleGroupViewState()
//    data class SuccessGetGroupsWithRelations(val groups: List<MuscleGroupModel>) :
//        MuscleGroupViewState()
//
//    object SuccessFetchMuscleSubGroups : MuscleGroupViewState()
//    object SuccessInsertMuscleGroupMuscleSubGroup : MuscleGroupViewState()
//    object SuccessFetchWorkouts : MuscleGroupViewState()
//    object Error : MuscleGroupViewState()
//    object Loading : MuscleGroupViewState()
//    object Empty : MuscleGroupViewState()
//}