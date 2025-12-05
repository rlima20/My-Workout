package com.example.myworkout.presentation.ui.navigation

import androidx.annotation.RequiresApi
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
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.defaultNavHostValues
import com.example.myworkout.presentation.ui.activity.props.Actions
import com.example.myworkout.presentation.ui.activity.props.MuscleGroupProps
import com.example.myworkout.presentation.ui.activity.props.TrainingProps
import com.example.myworkout.presentation.ui.components.MuscleConfig
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
import com.example.myworkout.presentation.ui.components.training.NewTraining
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    trainingViewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel,
    groupViewModel: MuscleGroupViewModel,
    navController: NavHostController,
    trainingProps: TrainingProps,
    muscleGroupProps: MuscleGroupProps,
    actions: Actions
) {
    val homeScreen: String = stringResource(R.string.home_screen)
    val muscleConfig: String = stringResource(R.string.muscle_config)
    val newTrainingScreen: String = stringResource(R.string.new_training)

    NavHostCompose(
        navController = navController,
        startDestination = HomeScreen.route,
        modifier = Modifier.defaultNavHostValues()
    ) {
        composable(route = HomeScreen.route) {
            actions.onChangeRouteToHomeScreen(true)
            actions.onChangeTopBarTitle(homeScreen)

            SetupTrainingStateObservers(
                workouts = muscleGroupProps.workouts,
                viewModel = trainingViewModel,
                muscleGroupViewModel = muscleGroupViewModel,
                listOfDays = trainingProps.listOfDays,
                trainingViewState = trainingProps.viewState,
                onChangeRoute = actions.onChangeRouteToHomeScreen,
                onNavigateToNewTraining = {
                    actions.onNavigateToGroupSubgroup()
                    trainingViewModel.fetchTrainings()
                },
                onDatabaseCreated = { actions.onDatabaseCreated() },
                onFetchWorkouts = { groupViewModel.fetchWorkouts(it) }
            )
        }

        composable(route = NewTraining.route) {
            actions.onChangeRouteToHomeScreen(false)
            actions.onChangeTopBarTitle(muscleConfig)

            MuscleConfig(
                viewModel = groupViewModel,
                muscleGroups = muscleGroupProps.muscleGroups,
                muscleSubGroups = muscleGroupProps.muscleSubGroups,
                muscleGroupsWithRelation = muscleGroupProps.muscleGroupsWithRelation,
                objSelected = muscleGroupProps.objSelected,
                onNavigateToNewTraining = { actions.onNavigateToNewTraining() },
            )

            SetupMuscleGroupStateObservers(
                muscleGroupViewState = muscleGroupProps.viewState,
                onDatabaseCreated = actions.onDatabaseCreated,
                onChangeRoute = actions.onChangeRouteToHomeScreen,
                onUpdateScreen = { trainingViewModel.fetchTrainings() },
                onNavigateToNewTraining = actions.onNavigateToGroupSubgroup,
            )
        }

        composable(route = New.route) {
            actions.onChangeRouteToHomeScreen(false)
            actions.onChangeTopBarTitle(newTrainingScreen)
            NewTraining(
                trainingViewModel = trainingViewModel,
                groupViewModel = groupViewModel,
                groupsWithRelations = muscleGroupProps.muscleGroupsWithRelation,
                subgroupsSelected = muscleGroupProps.subgroupsSelected,
                selectedGroup = muscleGroupProps.selectedGroup,
                listOfDays = trainingProps.listOfDays,
                trainingsQuantity = muscleGroupProps.workouts.size,
                onNavigateToHomeScreen = { actions.onNavigateToHomeScreen() }
            )
        }
    }
}

@Composable
private fun SetupMuscleGroupStateObservers(
    muscleGroupViewState: MuscleGroupViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onUpdateScreen: () -> Unit,
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

        is MuscleGroupViewState.Success -> { /* TODO */
        }

        is MuscleGroupViewState.SuccessDeleteGroup -> {
            onUpdateScreen()
        }
    }
}

@RequiresApi(35)
@Composable
private fun SetupTrainingStateObservers(
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>,
    viewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    trainingViewState: TrainingViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onFetchWorkouts: (trainings: List<TrainingModel>) -> Unit
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
                viewModel = viewModel,
                muscleGroupViewModel = muscleGroupViewModel,
                listOfDays = listOfDays,
                modifier = Modifier
            )
        }
    }
}