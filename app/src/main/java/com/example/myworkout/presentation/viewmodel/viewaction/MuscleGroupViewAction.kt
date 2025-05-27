package com.example.myworkout.presentation.viewmodel.viewaction

import com.example.myworkout.domain.model.MuscleSubGroupModel

sealed class MuscleGroupViewAction {
    data class CreateMuscleGroup(val name: String) : MuscleGroupViewAction()
    data class CreateMuscleSubGroup(val name: String) : MuscleGroupViewAction()
    data class CreateInitialDatabase(val isFirstInstall: Boolean) : MuscleGroupViewAction()
    data class AddNewSubGroupsSelected(val subGroup: MuscleSubGroupModel) : MuscleGroupViewAction()
    data class RemoveSubGroupsSelected(val subGroup: MuscleSubGroupModel) : MuscleGroupViewAction()
    data class SetNewSubGroupsSelected(val newList: MutableList<MuscleSubGroupModel>) : MuscleGroupViewAction()
    object FetchMuscleGroups : MuscleGroupViewAction()
    object FetchMuscleSubGroups : MuscleGroupViewAction()
    object SetupInitialState : MuscleGroupViewAction()
}