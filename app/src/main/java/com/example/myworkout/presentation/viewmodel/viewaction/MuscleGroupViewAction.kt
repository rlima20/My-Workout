package com.example.myworkout.presentation.viewmodel.viewaction

import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel

sealed class MuscleGroupViewAction {
    data class CreateMuscleGroup(val name: String) : MuscleGroupViewAction()
    data class CreateMuscleSubGroup(val name: String) : MuscleGroupViewAction()
    data class CreateInitialDatabase(val isFirstInstall: Boolean) : MuscleGroupViewAction()
    object GetSubGroupsFromRemoteConfig : MuscleGroupViewAction()
    data class SaveGroupSubGroupRelation(val newList: MutableList<MuscleGroupMuscleSubGroupModel>) : MuscleGroupViewAction()
    data class UpdateSubGroup(val subGroup: MuscleSubGroupModel): MuscleGroupViewAction()
    data class UpdateObjSelected(val objSelected: Pair<Int, Boolean>): MuscleGroupViewAction()
    data class GetRelationById(val muscleGroupId: Int): MuscleGroupViewAction()
    object FetchRelations : MuscleGroupViewAction()
    object FetchMuscleGroups : MuscleGroupViewAction()
    object FetchMuscleSubGroups : MuscleGroupViewAction()
    object ClearGroupsAndSubGroupsSelected : MuscleGroupViewAction()
    object SetupInitialState : MuscleGroupViewAction()
}