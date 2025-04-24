package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.extensions.navigateSingleTopTo
import com.example.myworkout.preferences.isNotFirstInstall
import com.example.myworkout.preferences.setFirstInstallValue
import com.example.myworkout.presentation.ui.components.home.TopBar
import com.example.myworkout.presentation.ui.navigation.HomeScreen
import com.example.myworkout.presentation.ui.navigation.NavHost
import com.example.myworkout.presentation.ui.navigation.NewTraining
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.TrainingViewAction
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.myworkout.presentation.ui.components.home.BottomAppBar as BottomBar

class MainActivity : ComponentActivity() {

    private val trainingViewModel: TrainingViewModel by viewModel()
    private val muscleGroupViewModel: MuscleGroupViewModel by viewModel()

    @RequiresApi(35)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val muscleGroupList by muscleGroupViewModel.listOfMuscleGroups.collectAsState(listOf())
            val muscleSubGroupList by muscleGroupViewModel.listOfMuscleSubGroups.collectAsState(
                listOf()
            )
            val muscleGroupViewState by muscleGroupViewModel.muscleGroupViewState.collectAsState()
            val trainingViewState by trainingViewModel.trainingViewState.collectAsState()
            val isHomeScreen by trainingViewModel.isHomeScreen.collectAsState()
            val appBarTitle by trainingViewModel.appBarTitle.collectAsState()
            val navController = rememberNavController()

            muscleGroupViewModel.setupDatabase(isNotFirstInstall(this.baseContext))

            MyWorkoutTheme {
                ScaffoldComponent(
                    appBarTitle = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    navController = navController,
                    muscleGroupList = muscleGroupList,
                    muscleSubGroupList = muscleSubGroupList,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState
                )
            }
        }
    }

    @RequiresApi(35)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun ScaffoldComponent(
        appBarTitle: String,
        isHomeScreen: Boolean,
        navController: NavHostController,
        muscleGroupList: List<MuscleGroupModel>,
        muscleSubGroupList: List<MuscleSubGroupModel>,
        muscleGroupViewState: MuscleGroupViewState,
        trainingViewState: TrainingViewState
    ) {
        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopBar(
                    title = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    onNavigateToHomeScreen = { navController.navigateSingleTopTo(HomeScreen.route) })
            },
            content = {
                NavHost(
                    navController = navController,
                    muscleGroupList = muscleGroupList,
                    muscleSubGroupList = muscleSubGroupList,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState,
                    onGetMuscleSubGroupsForTraining = {
                        muscleGroupViewModel.getMuscleSubGroupsForTraining(it)
                    },
                    onFetchTrainings = { trainingViewModel.fetchTrainings() },
                    onChangeRoute = { trainingViewModel.setIsHomeScreen(it) },
                    onChangeTopBarTitle = { trainingViewModel.setAppBarTitle(it) },
                    onNavigateToNewTraining = { navController.navigateSingleTopTo(NewTraining.route) },
                    onDatabaseCreated = {
                        if (isNotFirstInstall(this.baseContext) && isHomeScreen) {
                            LaunchedEffect(key1 = "") {
                                snackBarHostState.showSnackbar(getString(R.string.database_created_with_success))
                                setFirstInstallValue(this@MainActivity.baseContext, false)
                                trainingViewModel.dispatchViewAction(TrainingViewAction.CreateTrainings)
                            }
                        } else {
                            trainingViewModel.dispatchViewAction(TrainingViewAction.FetchTrainings)
                        }
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    onNavigateToHomeScreen = {
                        navController.navigateSingleTopTo(HomeScreen.route)
                    },
                    onNavigateToAddTrainingScreen = {
                        navController.navigateSingleTopTo(NewTraining.route)
                    }
                )
            }
        )
    }
}