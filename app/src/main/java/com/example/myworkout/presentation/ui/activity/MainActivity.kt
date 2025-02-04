package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myworkout.presentation.ui.navigation.HomeScreen
import com.example.myworkout.presentation.ui.navigation.NewTraining
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.extensions.navigateSingleTopTo
import com.example.myworkout.presentation.ui.components.home.TopBar
import com.example.myworkout.presentation.ui.navigation.NavHost
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme
import com.example.myworkout.presentation.viewmodel.DatabaseState
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.myworkout.presentation.ui.components.home.BottomAppBar as BottomBar

class MainActivity : ComponentActivity() {

    private val viewModel: TrainingViewModel by viewModel()

    @RequiresApi(35)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val trainingList by viewModel.listOfTrainings.collectAsState(listOf())
            val muscleSubGroupList by viewModel.listOfMuscleSubGroups.collectAsState(listOf())
            val databaseSetupState by viewModel.databaseOperationStatus.collectAsState()
            val isHomeScreen by viewModel.isHomeScreen.collectAsState()
            val appBarTitle by viewModel.appBarTitle.collectAsState()
            val navController = rememberNavController()

            MyWorkoutTheme {
                ScaffoldComponent(
                    appBarTitle = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    navController = navController,
                    trainingList = trainingList,
                    muscleSubGroupList = muscleSubGroupList,
                    databaseSetupState = databaseSetupState
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
        trainingList: List<TrainingModel>,
        muscleSubGroupList: List<MuscleSubGroupModel>,
        databaseSetupState: DatabaseState
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    onNavigateToHomeScreen = { navController.navigateSingleTopTo(HomeScreen.route) })
            },
            content = {
                NavHost(
                    navController = navController,
                    trainingList = trainingList,
                    listOfMuscleSubGroup = muscleSubGroupList,
                    databaseSetupState = databaseSetupState,
                    onGetMuscleSubGroupsForTraining = { viewModel.getMuscleSubGroupsForTraining(it) },
                    onFetchTrainings = { viewModel.fetchTrainings() },
                    onChangeRoute = { viewModel.setIsHomeScreen(it) },
                    onChangeTopBarTitle = { viewModel.setAppBarTitle(it) },
                    onNavigateToNewTraining = { navController.navigateSingleTopTo(NewTraining.route) }
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