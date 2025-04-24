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
    onFetchTrainings: () -> Unit,
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

            when (trainingViewState) {
                is TrainingViewState.InitialState -> { }
                is TrainingViewState.Loading -> {
                    LoadingComponent(info = "Criando treinamentos")
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

                is TrainingViewState.ErrorMessage -> {
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
            }

            when (muscleGroupViewState) {
                is MuscleGroupViewState.Success -> {
                    onDatabaseCreated()
                }

                is MuscleGroupViewState.Loading -> {
                    LoadingComponent(info = "Criando base de dados")
                }

                is MuscleGroupViewState.ErrorMessage -> {
                    ErrorStateComponent(onButtonClicked = {
                        onChangeRoute(true)
                        onChangeTopBarTitle(homeScreen)
                        onNavigateToNewTraining()
                    })
                }
            }
        }
        composable(route = NewTraining.route) {
            onChangeRoute(false)
            onChangeTopBarTitle(createNewTraining)
            TabRowComponent(muscleGroups = muscleGroupList)
        }
    }
}
