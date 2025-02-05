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
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.presentation.ui.components.home.EmptyStateComponent
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.LoadingComponent
import com.example.myworkout.presentation.viewmodel.DatabaseState
import androidx.navigation.compose.NavHost as NavHostCompose

@RequiresApi(35)
@Composable
fun NavHost(
    navController: NavHostController,
    trainingList: List<TrainingModel>,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    databaseSetupState: DatabaseState,
    onGetMuscleSubGroupsForTraining: (trainingId: Int) -> Unit,
    onFetchTrainings: () -> Unit,
    onChangeRoute: (value: Boolean) -> Unit,
    onChangeTopBarTitle: (title: String) -> Unit,
    onNavigateToNewTraining: () -> Unit
) {
    val homeScreen: String = stringResource(R.string.home_screen)
    val newTraining: String = stringResource(R.string.new_training)

    NavHostCompose(
        navController = navController,
        startDestination = HomeScreen.route,
        modifier = Modifier
    ) {
        composable(route = HomeScreen.route) {
            onChangeRoute(true)
            onChangeTopBarTitle(homeScreen)

            when (databaseSetupState) {
                DatabaseState.SUCCESS -> {
                    onGetMuscleSubGroupsForTraining(1) // Todo - Esse 1 vai ser dinâmico
                    onFetchTrainings()

                    HomeScreen(
                        trainingList,
                        listOfMuscleSubGroup
                    )
                }

                DatabaseState.ISLOADING -> {
                    LoadingComponent()
                }

                DatabaseState.ERROR -> {
                    ErrorStateComponent(onButtonClicked = {
                        onChangeRoute(true)
                        onChangeTopBarTitle(homeScreen)
                        onNavigateToNewTraining()
                    })
                }

                DatabaseState.ISEMPTY -> {
                    EmptyStateComponent(
                        modifier = Modifier.size(150.dp, 180.dp),
                        onClick = {
                        onChangeRoute(false)
                        onChangeTopBarTitle(newTraining)
                        onNavigateToNewTraining()
                    })
                }
            }
        }
        composable(route = NewTraining.route) {
            onChangeRoute(false)
            onChangeTopBarTitle(newTraining)
        }
    }
}
