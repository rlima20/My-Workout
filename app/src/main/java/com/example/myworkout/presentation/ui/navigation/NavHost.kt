package com.example.myworkout.presentation.ui.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.presentation.ui.components.NewMuscleGroupAndSubgroup
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
import com.example.myworkout.presentation.ui.components.training.NewTraining
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleGroupsWithRelation: List<MuscleGroupModel>,
    workouts: List<Pair<TrainingModel, List<MuscleSubGroupModel>>>,
    trainingViewState: TrainingViewState,
    muscleGroupViewState: MuscleGroupViewState,
    objSelected: Pair<Int, Boolean>,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onChangeRouteToHomeScreen: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onCreateMuscleGroup: (name: String) -> Unit,
    onUpdateSubGroup: (subGroup: MuscleSubGroupModel) -> Unit,
    onSaveRelation: (MutableList<MuscleGroupMuscleSubGroupModel>, MuscleGroupModel?) -> Unit,
    onNavigateToGroupSubgroup: () -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onFetchWorkouts: (trainings: List<TrainingModel>) -> Unit,
    groupsAndSubgroupsWithRelations: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>,
    onFetchRelations: () -> Unit
) {
    val homeScreen: String = stringResource(R.string.home_screen)
    val newTrainingScreen: String = stringResource(R.string.new_training)

    NavHostCompose(
        navController = navController,
        startDestination = HomeScreen.route,
        modifier = Modifier.background(colorResource(R.color.global_background_color))
    ) {
        composable(route = HomeScreen.route) {
            onChangeRouteToHomeScreen(true)
            onChangeTopBarTitle(homeScreen)

            SetupTrainingStateObservers(
                workouts = workouts,
                trainingViewState = trainingViewState,
                onTrainingChecked = { onTrainingChecked(it) },
                onChangeRoute = onChangeRouteToHomeScreen,
                onNavigateToNewTraining = onNavigateToGroupSubgroup,
                onDatabaseCreated = onDatabaseCreated,
                onFetchWorkouts = { onFetchWorkouts(it) }
            )
        }

        composable(route = NewTraining.route) {
            onChangeRouteToHomeScreen(false)
            onChangeTopBarTitle(newTrainingScreen)

            NewMuscleGroupAndSubgroup(
                muscleGroups = muscleGroups,
                muscleSubGroups = muscleSubGroups,
                muscleGroupsWithRelation = muscleGroupsWithRelation,
                objSelected = objSelected,
                onItemClick = { onItemClick(it) },
                onCreateMuscleGroup = { onCreateMuscleGroup(it) },
                onUpdateSubGroup = { onUpdateSubGroup(it) },
                onSaveRelation = { subGroups, group -> onSaveRelation(subGroups, group) },
                onNavigateToNewTraining = { onNavigateToNewTraining() }
            )

            SetupMuscleGroupStateObservers(
                muscleGroupViewState = muscleGroupViewState,
                onDatabaseCreated = onDatabaseCreated,
                onChangeRoute = onChangeRouteToHomeScreen,
                onNavigateToNewTraining = onNavigateToGroupSubgroup,
            )
        }

        composable(route = New.route) {
            onChangeRouteToHomeScreen(false)
            onChangeTopBarTitle(newTrainingScreen)
            NewTraining(
                groupsAndSubgroupsWithRelations = groupsAndSubgroupsWithRelations,
                onFetchRelations = {onFetchRelations()})
        }
    }
}

@Composable
private fun SetupMuscleGroupStateObservers(
    muscleGroupViewState: MuscleGroupViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
) {
    when (muscleGroupViewState) {
        is MuscleGroupViewState.Loading -> {
            LoadingComponent()
        }

        is MuscleGroupViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }

        is MuscleGroupViewState.DatabaseCreated -> {
            onDatabaseCreated()
        }

        MuscleGroupViewState.Success -> {
            // Todo - Futuramente, levar todos os estados para esse State
        }
    }
}

@RequiresApi(35)
@Composable
private fun SetupTrainingStateObservers(
    workouts: List<Pair<TrainingModel, List<MuscleSubGroupModel>>>,
    trainingViewState: TrainingViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onFetchWorkouts: (trainings: List<TrainingModel>) -> Unit,
) {
    when (trainingViewState) {
        is TrainingViewState.Loading -> {
            LoadingComponent()
        }

        is TrainingViewState.Empty -> {
            EmptyStateComponent(
                modifier = Modifier.size(150.dp, 180.dp),
                text = stringResource(R.string.new_training),
                painter = painterResource(R.drawable.add_icon),
                onClick = {
                    onChangeRoute(false)
                    onNavigateToNewTraining()
                },
                backgroundColor = colorResource(R.color.empty_state_color),
            )
            onDatabaseCreated()
        }

        is TrainingViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }

        is TrainingViewState.Success -> {
            onFetchWorkouts(trainingViewState.trainings)
            HomeScreen(
                workouts = workouts,
                modifier = Modifier,
                onTrainingChecked = { onTrainingChecked(it) },
                onGetMuscleSubGroupsByTrainingId = {}
            )
        }
    }
}