package com.example.myworkout.presentation.ui.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
import com.example.myworkout.presentation.ui.components.training.TabRowComponent
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.TrainingViewState
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    muscleGroupList: List<MuscleGroupModel>,
    muscleSubGroupList: List<MuscleSubGroupModel>,
    muscleGroupViewState: MuscleGroupViewState,
    trainingViewState: TrainingViewState,
    onGetMuscleSubGroupsForTraining: (trainingId: Int) -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit
) {
    val homeScreen: String = stringResource(R.string.home_screen)
    val createNewTraining: String = stringResource(R.string.new_training)

    NavHostCompose(
        navController = navController,
        startDestination = HomeScreen.route,
        modifier = Modifier
    ) {
        composable(route = HomeScreen.route) {
            onChangeRoute(true)
            onChangeTopBarTitle(homeScreen)

            setupTrainingStateObservers(
                trainingViewState,
                onChangeRoute,
                onChangeTopBarTitle,
                createNewTraining,
                onNavigateToNewTraining,
                onDatabaseCreated,
                homeScreen,
                muscleSubGroupList
            )

            setupMuscleGroupStateObservers(
                muscleGroupViewState,
                onDatabaseCreated,
                onChangeRoute,
                onChangeTopBarTitle,
                homeScreen,
                onNavigateToNewTraining,
                onGetMuscleSubGroupsForTraining
            )
        }
        composable(route = NewTraining.route) {
            onChangeRoute(false)
            onChangeTopBarTitle(createNewTraining)
            TabRowComponent(muscleGroups = muscleGroupList)
        }
    }
}

@Composable
private fun setupMuscleGroupStateObservers(
    muscleGroupViewState: MuscleGroupViewState,
    onDatabaseCreated: @Composable () -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    homeScreen: String,
    onNavigateToNewTraining: () -> Unit,
    onGetMuscleSubGroupsForTraining: (trainingId: Int) -> Unit,
) {
    when (muscleGroupViewState) {
        is MuscleGroupViewState.Success -> {
            onDatabaseCreated()
            onGetMuscleSubGroupsForTraining(0)
        }

        is MuscleGroupViewState.Loading -> {
            LoadingComponent(info = stringResource(R.string.preparing_everything))
        }

        is MuscleGroupViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onChangeTopBarTitle(homeScreen)
                onNavigateToNewTraining()
            })
        }

        else -> { /* Do nothing */
        }
    }
}

@Composable
private fun setupTrainingStateObservers(
    trainingViewState: TrainingViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    createNewTraining: String,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    homeScreen: String,
    muscleSubGroupList: List<MuscleSubGroupModel>
) {
    when (trainingViewState) {
        is TrainingViewState.Loading -> {
            LoadingComponent(info = stringResource(R.string.creating_trainings))
        }

        is TrainingViewState.Empty -> {
            EmptyStateComponent(
                modifier = Modifier.size(150.dp, 180.dp),
                onClick = {
                    onChangeRoute(false)
                    onChangeTopBarTitle(createNewTraining)
                    onNavigateToNewTraining()
                })
            onDatabaseCreated()
        }

        is TrainingViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onChangeTopBarTitle(homeScreen)
                onNavigateToNewTraining()
            })
        }

        is TrainingViewState.Success -> {
            HomeScreen(
                trainingViewState.trainingData,
                muscleSubGroupList
            )
        }

        else -> { /* Do nothing */
        }
    }
}
