package com.example.myworkout.presentation.ui.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.example.myworkout.enums.DayOfWeek
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
    dayOfWeek: String,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    objSelected: Pair<Int, Boolean>,
    setSelectedGroup: (MuscleGroupModel) -> Unit,
    selectedGroup: MuscleGroupModel,
    subgroupsSelected: List<MuscleSubGroupModel>,
    groupsWithRelations: List<MuscleGroupModel>,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onChangeRouteToHomeScreen: (Boolean) -> Unit,
    onChangeTopBarTitle: (String) -> Unit,
    onDatabaseCreated: @Composable (() -> Unit),
    onTrainingChecked: (TrainingModel) -> Unit,
    onCreateMuscleGroup: (String) -> Unit,
    onUpdateSubGroup: (MuscleSubGroupModel) -> Unit,
    onSaveRelation: (MutableList<MuscleGroupMuscleSubGroupModel>, MuscleGroupModel?) -> Unit,
    onNavigateToGroupSubgroup: () -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onFetchWorkouts: (List<TrainingModel>) -> Unit,
    onFetchRelations: () -> Unit,
    onSaveTraining: (TrainingModel, MuscleGroupModel) -> Unit,
    onFetchTrainings: () -> Unit,
    onUpdateDayOfWeek: (value: String) -> Unit,
    onEditGroup: (group: MuscleGroupModel) -> Unit,
    onDeleteGroup: (group: MuscleGroupModel) -> Unit
) {
    val homeScreen: String = stringResource(R.string.home_screen)
    val newTrainingScreen: String = stringResource(R.string.new_training)

    NavHostCompose(
        navController = navController,
        startDestination = HomeScreen.route,
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(R.color.global_background_color))
    ) {
        composable(route = HomeScreen.route) {
            onChangeRouteToHomeScreen(true)
            onChangeTopBarTitle(homeScreen)

            SetupTrainingStateObservers(
                workouts = workouts,
                listOfDays = listOfDays,
                trainingViewState = trainingViewState,
                dayOfWeek = dayOfWeek,
                onTrainingChecked = { onTrainingChecked(it) },
                onChangeRoute = onChangeRouteToHomeScreen,
                onNavigateToNewTraining = {
                    onNavigateToGroupSubgroup()
                    onFetchTrainings()
                },
                onDatabaseCreated = { onDatabaseCreated() },
                onFetchWorkouts = { onFetchWorkouts(it) },
                onUpdateDayOfWeek = { onUpdateDayOfWeek(it) }
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
                onNavigateToNewTraining = { onNavigateToNewTraining() },
                onEditGroup = { onEditGroup(it) },
                onDeleteGroup = { onDeleteGroup(it) }
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
                groupsWithRelations = groupsWithRelations,
                subgroupsSelected = subgroupsSelected,
                selectedGroup = selectedGroup,
                listOfDays = listOfDays,
                trainingsQuantity = workouts.size,
                onSetSelectedGroup = { setSelectedGroup(it) },
                onFetchRelations = { onFetchRelations() },
                onSaveTraining = { training, selectedGroup ->
                    onSaveTraining(training, selectedGroup)
                },
            )
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
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    trainingViewState: TrainingViewState,
    dayOfWeek: String,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onFetchWorkouts: (trainings: List<TrainingModel>) -> Unit,
    onUpdateDayOfWeek: (value: String) -> Unit,
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
            ErrorStateComponent(
                message = trainingViewState.trainingName,
                onButtonClicked = {
                    onChangeRoute(true)
                    onNavigateToNewTraining()
                })
        }

        is TrainingViewState.Success -> {
            onFetchWorkouts(trainingViewState.trainings)
            HomeScreen(
                workouts = workouts,
                dayOfWeek = dayOfWeek,
                listOfDays = listOfDays,
                modifier = Modifier,
                onTrainingChecked = { onTrainingChecked(it) },
                onUpdateDayOfWeek = { onUpdateDayOfWeek(it) }
            )
        }
    }
}