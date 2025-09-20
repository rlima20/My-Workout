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
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.Status
import com.example.myworkout.presentation.ui.components.NewMuscleGroupAndSubgroup
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
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
    muscleGroupsWithRelation: List<MuscleGroupModel>,
    trainingViewState: TrainingViewState,
    muscleGroupViewState: MuscleGroupViewState,
    objSelected: Pair<Int, Boolean>,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onFetchMuscleGroups: () -> Unit,
    onFetchMuscleSubGroups: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onCreateMuscleGroup: (name: String) -> Unit,
    onShowSnackBar: (message: String) -> Unit,
    onSetInitialState: () -> Unit,
    onUpdateSubGroup: (subGroup: MuscleSubGroupModel) -> Unit,
    onSaveRelation: (MutableList<MuscleGroupMuscleSubGroupModel>) -> Unit,
    onClearGroupsAndSubGroups: () -> Unit,
    onGetRelationById: (muscleGroupId: Int) -> Unit,
    onVerifyRelation: () -> Unit,
    onFetchGroupsWithRelations: () -> Unit,
    onGroupWithRelationClicked: (groupWithRelation: MuscleGroupModel) -> Unit
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

            SetupTrainingStateObservers(
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
                muscleGroups = muscleGroups,
                muscleSubGroups = muscleSubGroups,
                muscleGroupsWithRelation = muscleGroupsWithRelation,
                objSelected = objSelected,
                onItemClick = { onItemClick(it) },
                onGroupWithRelationClicked = { onGroupWithRelationClicked(it) },
                onCreateMuscleGroup = { onCreateMuscleGroup(it) },
                onUpdateSubGroup = { onUpdateSubGroup(it) },
                onSaveRelation = { relationList -> onSaveRelation(relationList) }
            )

            SetupMuscleGroupStateObservers(
                muscleGroupViewState = muscleGroupViewState,
                onDatabaseCreated = onDatabaseCreated,
                onChangeRoute = onChangeRoute,
                onNavigateToNewTraining = onNavigateToNewTraining,
                onFetchMuscleGroups = onFetchMuscleGroups,
                onFetchMuscleSubGroups = onFetchMuscleSubGroups,
                onShowToast = { onShowSnackBar(it) },
                onSetInitialState = { onSetInitialState() },
                onClearGroupsAndSubGroups = { onClearGroupsAndSubGroups() },
                onVerifyRelation = { onVerifyRelation() },
                onFetchGroupsWithRelations = { onFetchGroupsWithRelations() }
            )
        }
    }
}


@Composable
private fun SetupMuscleGroupStateObservers(
    muscleGroupViewState: MuscleGroupViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onFetchMuscleGroups: () -> Unit,
    onFetchMuscleSubGroups: () -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
    onShowToast: (message: String) -> Unit,
    onSetInitialState: () -> Unit,
    onClearGroupsAndSubGroups: () -> Unit,
    onVerifyRelation: () -> Unit,
    onFetchGroupsWithRelations: () -> Unit
) {
    when (muscleGroupViewState) {
        is MuscleGroupViewState.InitialState -> {
            onFetchMuscleGroups()
            onFetchMuscleSubGroups()
            onFetchGroupsWithRelations()
        }

        is MuscleGroupViewState.SuccessDatabaseCreated -> {
            onDatabaseCreated()
        }

        is MuscleGroupViewState.Loading -> {
            LoadingComponent()
        }

        is MuscleGroupViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }

        is MuscleGroupViewState.Empty -> {
            EmptyStateComponent(
                modifier = Modifier.size(150.dp, 180.dp),
                backgroundColor = colorResource(R.color.top_bar_color),
                text = stringResource(R.string.new_training),
                painter = painterResource(R.drawable.add_icon),
                onClick = {
                    onChangeRoute(false)
                    onNavigateToNewTraining()
                })
        }

        is MuscleGroupViewState.SuccessGetRelation -> {
            muscleGroupViewState.result
        }

        is MuscleGroupViewState.SuccessGetGroupsWithRelations -> {

        }

        MuscleGroupViewState.SuccessInsertMuscleGroup -> {
            onShowToast(stringResource(R.string.success_operation))
            onSetInitialState()
        }

        MuscleGroupViewState.SuccessInsertMuscleSubGroup -> {}
        MuscleGroupViewState.SuccessFetchMuscleGroups -> {}
        MuscleGroupViewState.SuccessFetchMuscleSubGroups -> {}

        MuscleGroupViewState.SuccessInsertMuscleGroupMuscleSubGroup -> {
            onShowToast(stringResource(R.string.success_operation))
            onVerifyRelation()
            onClearGroupsAndSubGroups()
        }
    }
}

@RequiresApi(35)
@Composable
private fun SetupTrainingStateObservers(
    trainingList: List<TrainingModel>,
    trainingViewState: TrainingViewState,
    onChangeRoute: (value: Boolean) -> Unit,
    onNavigateToNewTraining: () -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onDatabaseCreated: @Composable () -> Unit,
) {
    when (trainingViewState) {
        is TrainingViewState.Loading -> { /* Do nothing */
        }

        is TrainingViewState.Empty -> {
            val listOfTrainingAndSubGroups = listOf(
                Pair(
                    Constants().trainingMock(Status.ACHIEVED, "Peito e Tríceps"),
                    Constants().chestAndTricepsSubGroups
                ),
                Pair(
                    Constants().trainingMock(Status.PENDING, "Ombro"),
                    Constants().shoulderSubGroups
                ),
                Pair(
                    Constants().trainingMock(Status.MISSED, "Bíceps e Antebraço"),
                    Constants().bicepsSubGroups
                ),
                Pair(
                    Constants().trainingMock(Status.PENDING, "Costas e trapézio"),
                    Constants().backSubGroups
                )
            )

            HomeScreen(
                trainingAndSubGroups = listOfTrainingAndSubGroups,
                onTrainingChecked = {},
                onGetMuscleSubGroupsByTrainingId = {}
            )
//            EmptyStateComponent(
//                modifier = Modifier.size(150.dp, 180.dp),
//                text = stringResource(R.string.new_training),
//                painter = painterResource(R.drawable.add_icon),
//                onClick = {
//                    onChangeRoute(false)
//                    onNavigateToNewTraining()
//                },
//                backgroundColor = colorResource(R.color.top_bar_color),
//            )
            onDatabaseCreated()
        }

        is TrainingViewState.Error -> {
            ErrorStateComponent(onButtonClicked = {
                onChangeRoute(true)
                onNavigateToNewTraining()
            })
        }

        is TrainingViewState.Success -> { /* Do nothing */
        }

        else -> { /* Do nothing */
        }
    }
}
