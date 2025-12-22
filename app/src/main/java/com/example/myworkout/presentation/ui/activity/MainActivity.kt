package com.example.myworkout.presentation.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import com.example.myworkout.R
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.extensions.navigateSingleTopTo
import com.example.myworkout.preferences.TrainingPrefs
import com.example.myworkout.presentation.ui.activity.props.Actions
import com.example.myworkout.presentation.ui.activity.props.MuscleGroupProps
import com.example.myworkout.presentation.ui.activity.props.TrainingProps
import com.example.myworkout.presentation.ui.activity.props.muscleGroupProps
import com.example.myworkout.presentation.ui.activity.props.getTrainingProps
import com.example.myworkout.presentation.ui.components.home.TopBar
import com.example.myworkout.presentation.ui.navigation.HomeScreen
import com.example.myworkout.presentation.ui.navigation.NavHost
import com.example.myworkout.presentation.ui.navigation.New
import com.example.myworkout.presentation.ui.navigation.NewTraining
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
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

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = Color.TRANSPARENT

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }

        setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val trainingProps = getTrainingProps(trainingViewModel)
            val muscleGroupProps = muscleGroupProps(muscleGroupViewModel)
            val actions = Actions(
                onChangeRouteToHomeScreen = { isHome -> setIsHomeScreen(isHome) },
                onChangeTopBarTitle = { title -> trainingViewModel.setAppBarTitle(title) },
                onNavigateToGroupSubgroup = { navigateToNewTrainingScreen(trainingProps.navController) },
                onNavigateToNewTraining = { navigateToNewTraining(trainingProps.navController) },
                onNavigateToHomeScreen = { navigateToHomeScreen(trainingProps.navController) },
                onUpdateHomeScreenV2 = {
                    trainingProps.prefs.setHomeScreenV2(
                        this@MainActivity,
                        it
                    )
                },
                onDatabaseCreated = {
                    DatabaseCreationDone(
                        trainingProps.prefs,
                        trainingProps.isHomeScreen,
                        snackBarHostState,
                    )
                },
            )

            with(trainingProps) {
                setupInitialDatabase(prefs)
                fetchInfoIfNotFirstInstall(prefs, trainings)
                trainingViewModel.setHomeScreenV2(
                    trainingProps.prefs.getHomeScreenV2(this@MainActivity)
                )
                trainingProps.prefs
            }

            MyWorkoutTheme {
                ScaffoldComponent(
                    trainingProps = TrainingProps(
                        trainings = trainingProps.trainings,
                        viewState = trainingProps.viewState,
                        isHomeScreen = trainingProps.isHomeScreen,
                        appBarTitle = trainingProps.appBarTitle,
                        listOfDays = trainingProps.listOfDays,
                        navController = trainingProps.navController,
                        prefs = trainingProps.prefs,
                        isHomeScreenV2 = trainingProps.isHomeScreenV2
                    ),
                    muscleGroupProps = MuscleGroupProps(
                        workouts = muscleGroupProps.workouts,
                        muscleGroups = muscleGroupProps.muscleGroups,
                        muscleSubGroups = muscleGroupProps.muscleSubGroups,
                        subGroups = muscleGroupProps.subGroups,
                        subgroupsSelected = muscleGroupProps.subgroupsSelected,
                        selectedGroup = muscleGroupProps.selectedGroup,
                        viewState = muscleGroupProps.viewState,
                        muscleGroupsWithRelation = muscleGroupProps.muscleGroupsWithRelation,
                        objSelected = muscleGroupProps.objSelected,
                        selectedSort = muscleGroupProps.selectedSort,
                        query = muscleGroupProps.query,
                        noResult = muscleGroupProps.noResult
                    ),
                    snackBarHostState = snackBarHostState,
                    actions = actions
                )
            }
        }
    }

    @RequiresApi(35)
    @Composable
    private fun ScaffoldComponent(
        trainingProps: TrainingProps,
        muscleGroupProps: MuscleGroupProps,
        snackBarHostState: SnackbarHostState,
        actions: Actions
    ) {
        Scaffold(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),

            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopBar(
                    title = trainingProps.appBarTitle,
                    isHomeScreen = trainingProps.isHomeScreen,
                    onPopBackStack = {
                        clearGroupsAndSubGroupsSelected()
                        trainingProps.navController.navigateSingleTopTo(HomeScreen.route)
                    }
                )
            },
            content = { innerPadding ->
                NavHost(
                    modifier = Modifier
                        .padding(innerPadding)
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    trainingViewModel = trainingViewModel,
                    groupViewModel = muscleGroupViewModel,
                    muscleGroupViewModel = muscleGroupViewModel,
                    navController = trainingProps.navController,
                    trainingProps = trainingProps,
                    muscleGroupProps = muscleGroupProps,
                    actions = actions
                )
            },
            bottomBar = {
                BottomBar(
                    showNewTraining = muscleGroupProps.muscleGroupsWithRelation.isNotEmpty(),
                    onNavigateToHomeScreen = {
                        clearGroupsAndSubGroupsSelected()
                        navigateToHomeScreen(trainingProps.navController)
                    },
                    onNavigateToMuscleConfigScreen = {
                        clearGroupsAndSubGroupsSelected()
                        navigateToNewTrainingScreen(trainingProps.navController)
                    },
                    onNavigateToNewTrainingScreen = {
                        clearGroupsAndSubGroupsSelected()
                        navigateToNewTraining(trainingProps.navController)
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
            fetchSubGroups()
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

    private fun setIsHomeScreen(it: Boolean) {
        trainingViewModel.setIsHomeScreen(it)
    }

    private fun fetchTrainings() {
        trainingViewModel.fetchTrainings()
    }

    private fun fetchMuscleGroups() {
        muscleGroupViewModel.fetchMuscleGroups()
    }

    private fun fetchMuscleSubGroups() {
        muscleGroupViewModel.fetchMuscleSubGroups()
    }

    private fun fetchSubGroups() {
        muscleGroupViewModel.fetchSubGroups()
    }

    private fun setInstallValue(prefs: TrainingPrefs) {
        prefs.setFirstInstallValue(this@MainActivity.baseContext, true)
    }

    private suspend fun showSnackBar(snackBarHostState: SnackbarHostState) {
        snackBarHostState.showSnackbar(
            message = getString(R.string.everything_ready),
            duration = SnackbarDuration.Short
        )
    }

    private fun clearGroupsAndSubGroupsSelected() {
        muscleGroupViewModel.clearSubGroups()
    }

    private fun fetchWorkouts(trainings: List<TrainingModel>) {
        muscleGroupViewModel.fetchWorkouts(trainings)
    }

    private fun fetchRelations() {
        muscleGroupViewModel.getGroupsWithRelations()
    }

    private fun fetchGroupsAndSubGroupsWithRelations() {
        muscleGroupViewModel.getGroupsAndSubGroups()
    }
}