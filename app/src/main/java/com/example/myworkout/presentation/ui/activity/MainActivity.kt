package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.viewaction.MuscleGroupViewAction
import com.example.myworkout.presentation.viewmodel.viewaction.TrainingViewAction
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState
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
            val trainings by trainingViewModel.trainings.collectAsState(listOf())
            val muscleGroups by muscleGroupViewModel.muscleGroups.collectAsState(listOf())
            val muscleSubGroups by muscleGroupViewModel.muscleSubGroups.collectAsState()
            val muscleSubGroupsSelected by muscleGroupViewModel.muscleSubGroupsSelected.collectAsState()
            val newMuscleSubGroupsSelected by muscleGroupViewModel.newMuscleSubGroupsSelected.collectAsState()
            val muscleGroupViewState by muscleGroupViewModel.viewState.collectAsState()
            val trainingViewState by trainingViewModel.viewState.collectAsState()
            val isHomeScreen by trainingViewModel.isHomeScreen.collectAsState()
            val appBarTitle by trainingViewModel.appBarTitle.collectAsState()
            val navController = rememberNavController()
            val prefs = TrainingPrefs()

            setupDatabase(prefs)
            fetchInfoIfNotFirstInstall(prefs)

            MyWorkoutTheme {
                ScaffoldComponent(
                    appBarTitle = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    navController = navController,
                    trainings = trainings,
                    muscleGroups = muscleGroups,
                    muscleSubGroups = muscleSubGroups,
                    muscleSubGroupsSelected = muscleSubGroupsSelected,
                    newMuscleSubGroupsSelected = newMuscleSubGroupsSelected,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState,
                    prefs = prefs,
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
        trainings: List<TrainingModel>,
        muscleGroups: List<MuscleGroupModel>,
        muscleSubGroups: List<MuscleSubGroupModel>,
        muscleSubGroupsSelected: List<MuscleSubGroupModel>,
        newMuscleSubGroupsSelected: List<MuscleSubGroupModel>,
        muscleGroupViewState: MuscleGroupViewState,
        trainingViewState: TrainingViewState,
        prefs: TrainingPrefs,
    ) {
        val snackBarHostState = remember { SnackbarHostState() }
        var showMuscleGroupSection by remember { mutableStateOf(true) }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopBar(
                    title = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    onNavigateToHomeScreen = {
                        navController.navigateSingleTopTo(HomeScreen.route)
                        showMuscleGroupSection = true
                    })
            },
            content = {
                NavHost(
                    navController = navController,
                    trainings = trainings,
                    muscleGroups = muscleGroups,
                    muscleSubGroups = muscleSubGroups,
                    muscleSubGroupsSelected = muscleSubGroupsSelected,
                    newMuscleSubGroupsSelected = newMuscleSubGroupsSelected,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState,
                    onChangeRoute = { setIsHomeScreen(it) },
                    onChangeTopBarTitle = { setAppBarTitle(it) },
                    onNavigateToNewTraining = { navigateToNewTrainingScreen(navController) },
                    onDatabaseCreated = { DatabaseCreationDone(prefs, isHomeScreen, snackBarHostState,) },
                    onTrainingChecked = { },
                    onFetchMuscleGroups = { fetchMuscleGroups() },
                    onFetchMuscleSubGroups = { fetchMuscleSubGroups() },
                    onCreateMuscleGroup = { createMuscleGroup(it) },
                    onShowMuscleGroupSection = { showMuscleGroupSection = false },
                    onShowSnackBar = { showToast(message = it) },
                    onSetInitialState = { setInitialState() },
                    onAddSubGroupSelected = { addSubGroupSelected(it) },
                    onRemoveSubGroupSelected = { removeSubGroupSelected(it) },
                    onChangeNewMuscleSubGroupsSelected = { setNewMuscleSubGroupsSelected(it) }
                )
            },
            bottomBar = {
                BottomBar(
                    onNavigateToHomeScreen = {
                        navController.navigateSingleTopTo(HomeScreen.route)
                        showMuscleGroupSection = true
                    },
                    onNavigateToAddTrainingScreen = {
                        navigateToNewTrainingScreen(navController)
                    }
                )
            }
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }

    @Composable
    private fun DatabaseCreationDone(
        prefs: TrainingPrefs,
        isHomeScreen: Boolean,
        snackBarHostState: SnackbarHostState,
    ) {
        if (prefs.isFirstInstall(this.baseContext) && isHomeScreen) {
            LaunchedEffect(key1 = "") {
                trainingViewModel.dispatchViewAction(TrainingViewAction.SetEmptyState)
                showSnackBar(snackBarHostState)
                setInstallValue(prefs)
            }
        }
    }

    private fun fetchInfoIfNotFirstInstall(prefs: TrainingPrefs) {
        if (!prefs.isFirstInstall(this.baseContext)) {
            fetchTrainings()
            fetchMuscleGroups()
            fetchMuscleSubGroups()
        }
    }

    private fun setupDatabase(prefs: TrainingPrefs) {
        val isFirstInstall = prefs.isFirstInstall(this.baseContext)
        muscleGroupViewModel.dispatchViewAction(
            MuscleGroupViewAction.CreateInitialDatabase(isFirstInstall)
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

    private fun setInitialState() {
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.SetupInitialState)
    }

    private fun createMuscleGroup(it: String) {
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.CreateMuscleGroup(it))
    }

    private fun fetchMuscleGroups() {
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.FetchMuscleGroups)
    }

    private fun fetchMuscleSubGroups() {
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.FetchMuscleSubGroups)
    }

    private fun setInstallValue(prefs: TrainingPrefs) {
        prefs.setFirstInstallValue(this@MainActivity.baseContext, true)
    }

    private fun setNewMuscleSubGroupsSelected(newList: MutableList<MuscleSubGroupModel>){
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.SetNewSubGroupsSelected(newList))
    }

    private fun addSubGroupSelected(subGroup: MuscleSubGroupModel){
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.AddNewSubGroupsSelected(subGroup))

    }

    private fun removeSubGroupSelected(subGroup: MuscleSubGroupModel){
        muscleGroupViewModel.dispatchViewAction(MuscleGroupViewAction.RemoveSubGroupsSelected(subGroup))
    }

    private suspend fun showSnackBar(snackBarHostState: SnackbarHostState) {
        snackBarHostState.showSnackbar(getString(R.string.everything_ready))
    }

    private fun createTrainings() {
        trainingViewModel.dispatchViewAction(TrainingViewAction.NewTraining)
    }
}