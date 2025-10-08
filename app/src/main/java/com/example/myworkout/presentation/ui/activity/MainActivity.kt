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
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.extensions.navigateSingleTopTo
import com.example.myworkout.preferences.TrainingPrefs
import com.example.myworkout.presentation.ui.components.home.TopBar
import com.example.myworkout.presentation.ui.navigation.HomeScreen
import com.example.myworkout.presentation.ui.navigation.NavHost
import com.example.myworkout.presentation.ui.navigation.New
import com.example.myworkout.presentation.ui.navigation.NewTraining
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
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
            val workouts by muscleGroupViewModel.workouts.collectAsState()
            val muscleGroups by muscleGroupViewModel.muscleGroups.collectAsState(listOf())
            val muscleSubGroups by muscleGroupViewModel.muscleSubGroups.collectAsState()
            val muscleGroupViewState by muscleGroupViewModel.viewState.collectAsState()
            val muscleGroupsWithRelation by muscleGroupViewModel.muscleGroupsWithRelation.collectAsState()
            val groupsAndSubgroupsWithRelations by muscleGroupViewModel.groupsAndSubgroupsWithRelations.collectAsState()
            val objSelected by muscleGroupViewModel.objSelected.collectAsState()
            val trainingViewState by trainingViewModel.viewState.collectAsState()
            val isHomeScreen by trainingViewModel.isHomeScreen.collectAsState()
            val appBarTitle by trainingViewModel.appBarTitle.collectAsState()
            val navController = rememberNavController()
            val prefs = TrainingPrefs()

            setupInitialDatabase(prefs)
            fetchInfoIfNotFirstInstall(prefs, trainings)

            MyWorkoutTheme {
                ScaffoldComponent(
                    workouts = workouts,
                    appBarTitle = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    navController = navController,
                    muscleGroups = muscleGroups,
                    muscleSubGroups = muscleSubGroups,
                    muscleGroupsWithRelation = muscleGroupsWithRelation,
                    groupsAndSubgroupsWithRelations = groupsAndSubgroupsWithRelations,
                    muscleGroupViewState = muscleGroupViewState,
                    objSelected = objSelected,
                    trainingViewState = trainingViewState,
                    prefs = prefs,
                )
            }
        }
    }

    @RequiresApi(35)
    @Composable
    private fun ScaffoldComponent(
        workouts: List<Pair<TrainingModel, List<MuscleSubGroupModel>>>,
        appBarTitle: String,
        isHomeScreen: Boolean,
        navController: NavHostController,
        muscleGroups: List<MuscleGroupModel>,
        muscleSubGroups: List<MuscleSubGroupModel>,
        muscleGroupsWithRelation: List<MuscleGroupModel>,
        muscleGroupViewState: MuscleGroupViewState,
        objSelected: Pair<Int, Boolean>,
        trainingViewState: TrainingViewState,
        prefs: TrainingPrefs,
        groupsAndSubgroupsWithRelations: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>,
    ) {
        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopBar(
                    title = appBarTitle,
                    isHomeScreen = isHomeScreen,
                    onPopBackStack = { navController.navigateSingleTopTo(HomeScreen.route) })
            },
            content = {
                NavHost(
                    navController = navController,
                    muscleGroups = muscleGroups,
                    muscleSubGroups = muscleSubGroups,
                    muscleGroupsWithRelation = muscleGroupsWithRelation,
                    groupsAndSubgroupsWithRelations = groupsAndSubgroupsWithRelations,
                    workouts = workouts,
                    muscleGroupViewState = muscleGroupViewState,
                    trainingViewState = trainingViewState,
                    onItemClick = { setNewObjSelected(it) },
                    objSelected = objSelected,
                    onChangeRouteToHomeScreen = { setIsHomeScreen(it) },
                    onChangeTopBarTitle = { setAppBarTitle(it) },
                    onNavigateToGroupSubgroup = { navigateToNewTrainingScreen(navController) },
                    onDatabaseCreated = {
                        DatabaseCreationDone(
                            prefs,
                            isHomeScreen,
                            snackBarHostState,
                        )
                    },
                    onTrainingChecked = { updateTraining(it) },
                    onCreateMuscleGroup = { createMuscleGroup(it) },
                    onSaveRelation = { subGroups, group ->
                        saveGroupSubGroupRelation(subGroups)
                        showToast(
                            this@MainActivity.getString(
                                R.string.create_training_message,
                                group?.name
                            )
                        )
                    },
                    onUpdateSubGroup = { updateSubGroup(it) },
                    onFetchWorkouts = { fetchWorkouts(it) },
                    onNavigateToNewTraining = { navigateToNewTraining(navController) },
                    onFetchRelations = { fetchGroupsAndSubGroupsWithRelations() }
                )
            },
            bottomBar = {
                BottomBar(
                    onNavigateToHomeScreen = {
                        clearGroupsAndSubGroupsSelected()
                        navigateToHomeScreen(navController)
                    },
                    onNavigateToAddTrainingScreen = {
                        clearGroupsAndSubGroupsSelected()
                        navigateToNewTrainingScreen(navController)
                    }
                )
            }
        )
    }

    private fun navigateToHomeScreen(navController: NavHostController) {
        navController.navigateSingleTopTo(HomeScreen.route)
    }

    private fun navigateToNewTraining(navController: NavHostController) {
        navController.navigateSingleTopTo(New.route)
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
                trainingViewModel.setEmptyState()
                showSnackBar(snackBarHostState)
                setInstallValue(prefs)
            }
        }
    }

    private fun fetchInfoIfNotFirstInstall(prefs: TrainingPrefs, trainings: List<TrainingModel>) {
        if (!prefs.isFirstInstall(this.baseContext)) {
            fetchTrainings()
            fetchMuscleGroups()
            fetchMuscleSubGroups()
            fetchRelations()
            fetchWorkouts(trainings)
            fetchGroupsAndSubGroupsWithRelations()
        }
    }

    private fun setupInitialDatabase(prefs: TrainingPrefs) {
        val isFirstInstall = prefs.isFirstInstall(this.baseContext)
        muscleGroupViewModel.createInitialDatabase(isFirstInstall)
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
        trainingViewModel.fetchTrainings()
    }

    private fun createMuscleGroup(it: String) {
        muscleGroupViewModel.insertMuscleGroup(it, BodyPart.OTHER)
    }

    private fun fetchMuscleGroups() {
        muscleGroupViewModel.fetchMuscleGroups()
    }

    private fun fetchMuscleSubGroups() {
        muscleGroupViewModel.fetchMuscleSubGroups()
    }

    private fun setInstallValue(prefs: TrainingPrefs) {
        prefs.setFirstInstallValue(this@MainActivity.baseContext, true)
    }

    private fun saveGroupSubGroupRelation(list: MutableList<MuscleGroupMuscleSubGroupModel>) {
        muscleGroupViewModel.insertMuscleGroupMuscleSubGroup(list)
    }

    private suspend fun showSnackBar(snackBarHostState: SnackbarHostState) {
        snackBarHostState.showSnackbar(getString(R.string.everything_ready))
    }

    private fun clearGroupsAndSubGroupsSelected() {
        muscleGroupViewModel.clearSubGroups()
    }

    private fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        muscleGroupViewModel.updateSubGroup(subGroup)
    }

    private fun setNewObjSelected(objSelected: Pair<Int, Boolean>) {
        muscleGroupViewModel.setMuscleGroupSelected(objSelected)
    }

    private fun fetchWorkouts(trainings: List<TrainingModel>) {
        muscleGroupViewModel.fetchWorkouts(trainings)
    }

    private fun fetchRelations() {
        muscleGroupViewModel.getGroupsWithRelations()
    }

    private fun updateTraining(trainingModel: TrainingModel) {
        trainingViewModel.updateTraining(trainingModel)
    }

    private fun fetchGroupsAndSubGroupsWithRelations() {
        muscleGroupViewModel.getGroupsAndSubGroups()
    }
}