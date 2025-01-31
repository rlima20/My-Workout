package com.example.myworkout.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.Constants
import com.example.myworkout.enums.Status
import com.example.myworkout.domain.model.TrainingModel
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

        // Vem da ViewModel
        var trainingList: MutableList<TrainingModel> = mutableListOf()

        setContent {

//            Status.entries.forEach { _ ->
//                viewModel.viewState.value?.trainings?.let {
//                    trainingList = it.toMutableList()
//                }
//            }

            MyWorkoutTheme {
                Scaffold(
                    topBar = { TopBar() },
                    content = { HomeScreen(trainingList) },
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
        HomeScreen(mutableListOf(Constants().trainingMock(Status.PENDING)))
    }
}