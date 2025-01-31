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
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.Constants
import com.example.myworkout.enums.Status
import com.example.myworkout.preferences.isFirstInstall
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.ui.components.home.TopBar
import com.example.myworkout.presentation.ui.theme.MyWorkoutTheme
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.myworkout.presentation.ui.components.home.BottomAppBar as BottomBar

class MainActivity : ComponentActivity() {

    private val viewModel: TrainingViewModel by viewModel()

    @RequiresApi(35)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val trainingList by viewModel.listOfTrainings.collectAsState(listOf())
            val listOfMuscleSubGroup by viewModel.listOfMuscleSubGroups.collectAsState(listOf())

            viewModel.setupDatabase(isFirstInstall(this.baseContext))
            viewModel.getMuscleSubGroupsForTraining(1)
            viewModel.fetchTrainings()

            MyWorkoutTheme {
                Scaffold(
                    topBar = { TopBar() },
                    content = {
                        HomeScreen(
                            trainingList,
                            listOfMuscleSubGroup
                        )
                    },
                    bottomBar = {
                        BottomBar(
                            onNavigateToHomeScreen = { navigateToHomeScreen() },
                            onNavigateToAddTrainingScreen = { navigateToAddTrainingScreen() }
                        )
                    }
                )
            }
        }
    }
}

private fun navigateToAddTrainingScreen() {}

private fun navigateToHomeScreen() {}


@RequiresApi(35)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyWorkoutTheme {
        HomeScreen(
            mutableListOf(Constants().trainingMock(Status.PENDING)),
            listOf()
        )
    }
}