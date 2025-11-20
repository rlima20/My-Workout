package com.example.myworkout.presentation.ui.activity.props

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingSubGroupState
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState

data class MuscleGroupProps(
    val workouts: List<Pair<TrainingModel, List<TrainingSubGroupState>>>,
    val muscleGroups: List<MuscleGroupModel>,
    val muscleSubGroups: List<MuscleSubGroupModel>,
    val subGroups: List<SubGroupModel>,
    val subgroupsSelected: List<MuscleSubGroupModel>,
    val selectedGroup: MuscleGroupModel,
    val viewState: MuscleGroupViewState,
    val muscleGroupsWithRelation: List<MuscleGroupModel>,
    val objSelected: Pair<Int, Boolean>
)

@Composable
fun muscleGroupProps(
    muscleGroupViewModel: MuscleGroupViewModel
): MuscleGroupProps {
    val workouts by muscleGroupViewModel.newWorkouts.collectAsState()
    val muscleGroups by muscleGroupViewModel.muscleGroups.collectAsState(listOf())
    val muscleSubGroups by muscleGroupViewModel.muscleSubGroups.collectAsState()
    val subGroups by muscleGroupViewModel.subGroups.collectAsState()
    val subgroupsSelected by muscleGroupViewModel.subgroupsSelected.collectAsState()
    val selectedGroup by muscleGroupViewModel.selectedGroup.collectAsState()
    val viewState by muscleGroupViewModel.viewState.collectAsState()
    val muscleGroupsWithRelation by muscleGroupViewModel.muscleGroupsWithRelation.collectAsState()
    val objSelected by muscleGroupViewModel.objSelected.collectAsState()

    return MuscleGroupProps(
        workouts = workouts,
        muscleGroups = muscleGroups,
        muscleSubGroups = muscleSubGroups,
        subGroups = subGroups,
        subgroupsSelected = subgroupsSelected,
        selectedGroup = selectedGroup,
        viewState = viewState,
        muscleGroupsWithRelation = muscleGroupsWithRelation,
        objSelected = objSelected
    )
}