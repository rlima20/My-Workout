package com.example.myworkout.presentation.ui.components.home.homev2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT_V2
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.ui.components.commons.ToggleItem
import com.example.myworkout.presentation.ui.components.home.HomeScreen
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModelFake
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModelFake

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun HomeScreenManager(
    modifier: Modifier,
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    viewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel,
    isHomeScreenV2: Boolean,
) {

    var innerHomeScreenV2 by remember { mutableStateOf(isHomeScreenV2) }

    LaunchedEffect(isHomeScreenV2) {
        innerHomeScreenV2 = isHomeScreenV2
    }

    Column(modifier = Modifier.padding(top = 50.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            ToggleItem(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp, end = 16.dp),
                label = "Home 2.0",
                selected = isHomeScreenV2,
                onClick = { viewModel.setHomeScreenV2(!isHomeScreenV2) }
            )
        }

        if (innerHomeScreenV2) {
            HomeScreenV2(
                modifier = modifier,
                workouts = workouts,
                listOfDays = listOfDays,
                viewModel = viewModel,
                muscleGroupViewModel = muscleGroupViewModel,
                trainingCardProps = TrainingCardProps(
                    modifier = Modifier,
                    topBarHeight = TRAINING_NAME_MAX_HEIGHT_V2,
                    chipHeight = 40.dp,
                    cardHeight = 350.dp,
                    trainingNameFontSize = 18.sp
                )
            )
        } else {
            HomeScreen(
                modifier = modifier,
                workouts = workouts,
                listOfDays = listOfDays,
                viewModel = viewModel,
                muscleGroupViewModel = muscleGroupViewModel,
                trainingCardProps = TrainingCardProps(
                    modifier = Modifier,
                    topBarHeight = TRAINING_NAME_MAX_HEIGHT,
                    chipHeight = 30.dp,
                    cardHeight = null,
                    trainingNameFontSize = 12.sp
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreenManager(
        modifier = Modifier,
        workouts = Constants().getNewTrainingAndSubGroupsHomeScreenMock(),
        listOfDays = Constants().getListOfDays(),
        viewModel = TrainingViewModelFake(),
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        isHomeScreenV2 = true
    )
}