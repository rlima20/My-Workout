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
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
import com.example.myworkout.presentation.ui.components.training.NewTraining
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.TrainingViewState
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    trainingList: List<TrainingModel>,
    listOfMapOfMuscleGroupMuscleSubGroups: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>,
    muscleGroupViewState: MuscleGroupViewState,
    trainingViewState: TrainingViewState,
    listOfMuscleSubGroupById: List<MuscleSubGroupModel>,
    onGetMuscleGroupMuscleSubGroup: () -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onFetchMuscleGroups: () -> Unit,
    onGetMuscleSubGroupsByTrainingId: (trainingId: Int) -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit
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
                trainingList = trainingList,
                listOfMapOfMuscleGroupMuscleSubGroup = listOfMapOfMuscleGroupMuscleSubGroups,
                trainingViewState = trainingViewState,
                onTrainingChecked = { onTrainingChecked(it) },
                onChangeRoute = onChangeRoute,
                onNavigateToNewTraining = onNavigateToNewTraining,
                onDatabaseCreated = onDatabaseCreated,
                listOfMuscleSubGroupById = listOfMuscleSubGroupById,
                onGetMuscleSubGroupsByTrainingId = { onGetMuscleSubGroupsByTrainingId(it) }
            )

            setupMuscleGroupStateObservers(
                muscleGroupViewState = muscleGroupViewState,
                onDatabaseCreated = onDatabaseCreated,
                onChangeRoute = onChangeRoute,
                onNavigateToNewTraining = onNavigateToNewTraining,
                onGetMuscleGroupMuscleSubGroup = onGetMuscleGroupMuscleSubGroup,
                onFetchMuscleGroups = onFetchMuscleGroups
            )
        }

        composable(route = NewTraining.route) {
            onChangeRoute(false)
            onChangeTopBarTitle(createNewTraining)
            NewTraining(listOfMapOfMuscleGroupMuscleSubGroups)
        }
    }
}

@Composable
private fun setupMuscleGroupStateObservers(
    muscleGroupViewState: MuscleGroupViewState,
    onDatabaseCreated: @Composable () -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onGetMuscleGroupMuscleSubGroup: () -> Unit,
    onFetchMuscleGroups: () -> Unit
) {
    when (muscleGroupViewState) {
        is MuscleGroupViewState.Success -> {
            onDatabaseCreated()
            onGetMuscleGroupMuscleSubGroup()
            onFetchMuscleGroups()
        }

        is MuscleGroupViewState.Loading -> {
            LoadingComponent(text = stringResource(R.string.preparing_everything))
        }

        is MuscleGroupViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }

        else -> { /* Do nothing */
        }
    }
}

@Composable
private fun setupTrainingStateObservers(
    trainingList: List<TrainingModel>,
    listOfMapOfMuscleGroupMuscleSubGroup: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>,
    listOfMuscleSubGroupById: List<MuscleSubGroupModel>,
    trainingViewState: TrainingViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onGetMuscleSubGroupsByTrainingId: (trainingId: Int) -> Unit
) {
    when (trainingViewState) {
        is TrainingViewState.Loading -> {
            LoadingComponent(text = stringResource(R.string.creating_trainings))
        }

        is TrainingViewState.Empty -> {
            EmptyStateComponent(
                modifier = Modifier.size(150.dp, 180.dp),
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

        is TrainingViewState.Success -> {
            // Todo - Preciso passar para muscleSubGroupList os muscleSubGroups ligados à
            // todo - ligados ao training em questao.
            HomeScreen(
                trainingList = trainingList,
                muscleSubGroupList = listOfMuscleSubGroupById,//  muscleSubGroupList.flatMap { it.value }, // todo pegar esse cara e passar pra frente
                onTrainingChecked = { onTrainingChecked(it) },
                onGetMuscleSubGroupsByTrainingId = { onGetMuscleSubGroupsByTrainingId(it) }
            )
            trainingList.forEach {
                onGetMuscleSubGroupsByTrainingId(it.trainingId)
            }
        }

        else -> { /* Do nothing */
        }
    }
}
