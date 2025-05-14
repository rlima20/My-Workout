package com.example.myworkout.presentation.ui.navigation

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
import com.example.myworkout.presentation.ui.components.NewMuscleGroupAndSubgroup
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    trainings: List<TrainingModel>,
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    trainingViewState: TrainingViewState,
    muscleGroupViewState: MuscleGroupViewState,
    showMuscleGroupSection: Boolean,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onFetchMuscleGroups: () -> Unit,
    onFetchMuscleSubGroups: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onCreateMuscleGroup: (name: String) -> Unit,
    onShowMuscleGroupSection: () -> Unit
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
                trainingList = trainings,
                trainingViewState = trainingViewState,
                onTrainingChecked = { onTrainingChecked(it) },
                onChangeRoute = onChangeRoute,
                onNavigateToNewTraining = onNavigateToNewTraining,
                onDatabaseCreated = onDatabaseCreated,
            )
        }

        composable(route = NewTraining.route) {
            onChangeRoute(false)
            onChangeTopBarTitle(createNewTraining)

            NewMuscleGroupAndSubgroup(
                showMuscleGroupSection = showMuscleGroupSection ,
                onCreateMuscleGroup = { onCreateMuscleGroup(it) },
                onCreateMuscleSubGroup = { }
            )

            setupMuscleGroupStateObservers(
                muscleGroupViewState = muscleGroupViewState,
                showMuscleGroupSection = { onShowMuscleGroupSection() },
                onDatabaseCreated = onDatabaseCreated,
                onChangeRoute = onChangeRoute,
                onNavigateToNewTraining = onNavigateToNewTraining,
                onFetchMuscleGroups = onFetchMuscleGroups,
                onFetchMuscleSubGroups = onFetchMuscleSubGroups
            )
        }
    }
}


@Composable
private fun setupMuscleGroupStateObservers(
    muscleGroupViewState: MuscleGroupViewState,
    showMuscleGroupSection: () -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onFetchMuscleGroups: () -> Unit,
    onFetchMuscleSubGroups: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    ) {
    when (muscleGroupViewState) {
        is MuscleGroupViewState.Success -> {
            onDatabaseCreated()
            // showMuscleGroupSection()
            onFetchMuscleGroups()
            onFetchMuscleSubGroups()
        }
        is MuscleGroupViewState.Loading -> {
            LoadingComponent(text = stringResource(R.string.loading))
        }
        is MuscleGroupViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }
        else -> { /* Do nothing */ }
    }
}

@Composable
private fun setupTrainingStateObservers(
    trainingList: List<TrainingModel>,
    trainingViewState: TrainingViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
) {
    when (trainingViewState) {
        is TrainingViewState.Loading -> { /* Do nothing */ }

        is TrainingViewState.Empty -> {
            EmptyStateComponent(
                modifier = Modifier.size(150.dp, 180.dp),
                text = stringResource(R.string.new_training),
                painter = painterResource(R.drawable.add_icon),
                onClick = {
                    onChangeRoute(false)
                    onNavigateToNewTraining()
                })
            onDatabaseCreated()
        }

        is TrainingViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }

        is TrainingViewState.Success -> { /* Do nothing */ }
        else -> { /* Do nothing */ }
    }
}
