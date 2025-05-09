package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Build
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
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.extensions.navigateSingleTopTo
import com.example.myworkout.preferences.TrainingPrefs
import com.example.myworkout.presentation.ui.components.home.TopBar
import com.example.myworkout.presentation.ui.navigation.HomeScreen
import com.example.myworkout.presentation.ui.navigation.NavHost
import com.example.myworkout.presentation.ui.navigation.NewTraining
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewAction
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.TrainingViewAction
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.myworkout.presentation.ui.components.home.BottomAppBar as BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    private val trainingViewModel: TrainingViewModel by viewModel()
    private val muscleGroupViewModel: MuscleGroupViewModel by viewModel()

    @RequiresApi(35)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val trainingList by trainingViewModel.listOfTrainings.collectAsState(listOf())
            val muscleGroupList by muscleGroupViewModel.listOfMuscleGroups.collectAsState(listOf())
            val listOfMuscleSubGroupById by muscleGroupViewModel.listOfMuscleSubGroupsById.collectAsState(listOf())
            val muscleSubGroupList by muscleGroupViewModel.mapOfMuscleGroupsMuscleSubGroups.collectAsState()
            val muscleGroupViewState by muscleGroupViewModel.muscleGroupViewState.collectAsState()
            val trainingViewState by trainingViewModel.trainingViewState.collectAsState()
            val isHomeScreen by trainingViewModel.isHomeScreen.collectAsState()
            val appBarTitle by trainingViewModel.appBarTitle.collectAsState()
            val navController = rememberNavController()
            val prefs = TrainingPrefs()
            val isDevMode = trainingViewModel.isDevMode

            setupDatabase(prefs)
            fetchInfoIfNotFirstInstall(prefs, isHomeScreen)

            MyWorkoutTheme {
                ScaffoldComponent(
                    appBarTitle = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    navController = navController,
                    trainingList = trainingList,
                    muscleGroupList = muscleGroupList,
                    muscleSubGroupList = muscleSubGroupList,
                    listOfMuscleSubGroupById = listOfMuscleSubGroupById,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState,
                    prefs = prefs,
                    isDevMode = isDevMode
                )
            }
        }
    }

    @RequiresApi(35)
    @Composable
    private fun ScaffoldComponent(
        appBarTitle: String,
        isHomeScreen: Boolean,
        navController: NavHostController,
        trainingList: List<TrainingModel>,
        muscleGroupList: List<MuscleGroupModel>,
        listOfMuscleSubGroupById: List<MuscleSubGroupModel>,
        muscleSubGroupList: Map<MuscleGroupModel, List<MuscleSubGroupModel>>,
        muscleGroupViewState: MuscleGroupViewState,
        trainingViewState: TrainingViewState,
        prefs: TrainingPrefs,
        isDevMode: Boolean
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
                    trainingList = trainingList,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState,
                    listOfMuscleSubGroupById = listOfMuscleSubGroupById,
                    onGetMuscleGroupMuscleSubGroup = { }, // Todo - remover isso depois
                    onChangeRoute = { setIsHomeScreen(it) },
                    onChangeTopBarTitle = { setAppBarTitle(it) },
                    onNavigateToNewTraining = { navigateToNewTrainingScreen(navController) },
                    onDatabaseCreated = {
                        DatabaseCreationDone(
                            prefs,
                            isHomeScreen,
                            snackBarHostState,
                            isDevMode
                        )
                    },
                    onTrainingChecked = { },
                    onFetchMuscleGroups = { fetchMuscleGroups() },
                    onGetMuscleSubGroupsByTrainingId = {
                        /**
                         * todo()
                         * A partir daqui eu preciso atualizar e passar pra dentro do TrainingCard
                         * o muscleSUbGroupList atualizado.
                         * Esse objeto terá os subgrupos relacionados ao treinamento em questão.
                         */
                        muscleGroupViewModel.dispatchViewAction(
                            MuscleGroupViewAction.FetchMuscleSubGroupsByTrainingId(it)
                        )
                    },
                    listOfMapOfMuscleGroupMuscleSubGroups = listOf(muscleSubGroupList)
                )
            },
            bottomBar = {
                BottomBar(
                    onNavigateToHomeScreen = {
                        navController.navigateSingleTopTo(HomeScreen.route)
                    },
                    onNavigateToAddTrainingScreen = {
                        navigateToNewTrainingScreen(navController)
                    }
                )
            }
        )
    }

    @Composable
    private fun DatabaseCreationDone(
        prefs: TrainingPrefs,
        isHomeScreen: Boolean,
        snackBarHostState: SnackbarHostState,
        isDevMode: Boolean
    ) {
        if (prefs.isNotFirstInstall(this.baseContext) && isHomeScreen) {
            LaunchedEffect(key1 = "") {
                validateDevMode(isDevMode)
                showSnackBar(snackBarHostState)
                setInstallValue(prefs)
            }
        }
    }

    // Todo - levar essa regra para dentro do viewmodel
    private fun validateDevMode(isDevMode: Boolean) {
        if (isDevMode) {
            createTrainings()
        } else {
            trainingViewModel.dispatchViewAction(TrainingViewAction.SetEmptyState)
        }
    }

    private fun fetchInfoIfNotFirstInstall(
        prefs: TrainingPrefs,
        isHomeScreen: Boolean
    ) {
        if (!(prefs.isNotFirstInstall(this.baseContext) && isHomeScreen)) {
            fetchTrainings()
            fetchMuscleGroups()
            fetchMuscleSubGroups()
        }
    }

    private fun setupDatabase(prefs: TrainingPrefs) {
        muscleGroupViewModel.dispatchViewAction(
            MuscleGroupViewAction.SetupDatabase(prefs.isNotFirstInstall(this.baseContext))
        )
    }

    private fun navigateToNewTrainingScreen(navController: NavHostController) {
        navController.navigateSingleTopTo(NewTraining.route)
    }

    private fun setAppBarTitle(it: String) {
        trainingViewModel.setAppBarTitle(it)
    }

    private fun setIsHomeScreen(it: Boolean) {
        trainingViewModel.setIsHomeScreen(it)
    }

    private fun fetchTrainings() {
        trainingViewModel.dispatchViewAction(TrainingViewAction.FetchTrainings)
    }

    private fun fetchMuscleGroups() {
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.FetchMuscleGroups)
    }

    private fun fetchMuscleSubGroups() {
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.FetchMuscleSubGroups)
    }

    private fun createTrainings() {
        trainingViewModel.dispatchViewAction(TrainingViewAction.CreateTrainings)
    }

    private fun getMuscleSubGroupsByTrainingId(trainingId: Int) {
        muscleGroupViewModel.dispatchViewAction(
            MuscleGroupViewAction.FetchMuscleSubGroupsByTrainingId(trainingId)
        )
    }

    private fun setInstallValue(prefs: TrainingPrefs) {
        prefs.setFirstInstallValue(this@MainActivity.baseContext, false)
    }

    private suspend fun showSnackBar(snackBarHostState: SnackbarHostState) {
        snackBarHostState.showSnackbar(getString(R.string.everything_ready))
    }
}