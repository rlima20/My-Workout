package com.example.myworkout.presentation.ui.activity.props

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.preferences.TrainingPrefs
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.viewstate.TrainingViewState

data class TrainingProps(
    val trainings: List<TrainingModel>,
    val viewState: TrainingViewState,
    val isHomeScreen: Boolean,
    val appBarTitle: String,
    val listOfDays: List<Pair<DayOfWeek, Boolean>>,
    val navController: NavHostController,
    val prefs: TrainingPrefs
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun trainingProps(trainingViewModel: TrainingViewModel): TrainingProps {
    val trainings by trainingViewModel.trainings.collectAsState(listOf())
    val viewState by trainingViewModel.viewState.collectAsState()
    val isHomeScreen by trainingViewModel.isHomeScreen.collectAsState()
    val appBarTitle by trainingViewModel.appBarTitle.collectAsState()
    val listOfDays by trainingViewModel.listOfDays.collectAsState()
    val navController = rememberNavController()
    val prefs = TrainingPrefs()

    return TrainingProps(
        trainings = trainings,
        viewState = viewState,
        isHomeScreen = isHomeScreen,
        appBarTitle = appBarTitle,
        listOfDays = listOfDays,
        navController = navController,
        prefs = prefs
    )
}