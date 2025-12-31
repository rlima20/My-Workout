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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynutrition.domain.model.macro.MacroResult
import com.example.mynutrition.domain.model.nutrition.NutritionResult
import com.example.mynutrition.presentation.nutrition.components.NutritionCard
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT_V2
import com.example.myworkout.R
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.ui.components.commons.ToggleItem
import com.example.myworkout.presentation.ui.components.commons.Tooltip
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
    showNutritionCard: Boolean,
    onHomeScreenV2: (value: Boolean) -> Unit,
    onSetNutritionCard: (value: Boolean) -> Unit,
) {

    var innerHomeScreenV2 by remember { mutableStateOf(isHomeScreenV2) }

    LaunchedEffect(isHomeScreenV2) {
        innerHomeScreenV2 = isHomeScreenV2
    }

    Column(modifier = Modifier.padding(top = 50.dp)) {
        ToggleItemSection(isHomeScreenV2, onHomeScreenV2, viewModel)
        NutritionCardSection(
            showNutritionCard = showNutritionCard,
            onSetNutritionCard = { onSetNutritionCard(it) })
        HomeScreenSection(
            innerHomeScreenV2,
            modifier,
            workouts,
            listOfDays,
            viewModel,
            muscleGroupViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ToggleItemSection(
    isHomeScreenV2: Boolean,
    onHomeScreenV2: (Boolean) -> Unit,
    viewModel: TrainingViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        ToggleItem(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp, end = 16.dp),
            label = stringResource(R.string.home_2),
            selected = isHomeScreenV2,
            selectedColor = colorResource(R.color.button_color_2),
            onClick = {
                val newValue = !isHomeScreenV2
                onHomeScreenV2(newValue)
                viewModel.setHomeScreenV2(newValue)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
private fun HomeScreenSection(
    innerHomeScreenV2: Boolean,
    modifier: Modifier,
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    viewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel
) {
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
                chipHeight = 35.dp,
                cardHeight = 330.dp,
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

@Composable
private fun NutritionCardSection(
    showNutritionCard: Boolean,
    onSetNutritionCard: (value: Boolean) -> Unit,
) {
    if (showNutritionCard) {
        NutritionCard(
            modifier = Modifier
                .padding(
                    top = 2.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            circularStrokeWidth = 7.dp,
            kcalTextSize = 12.sp,
            nutritionResult = NutritionResult(
                tmb = 2000,
                maintenanceCalories = 2000,
                calorieGoal = 2500,
                macros = MacroResult(
                    100,
                    100,
                    100,
                    100,
                    100,
                    100,
                    100,
                    100
                )
            )
        )
    } else {
        Tooltip(
            modifier = Modifier.padding(horizontal = 16.dp),
            backgroundColor = R.color.warning_home,
            fontSize = 12.sp,
            icon = painterResource(R.drawable.baseline_info_24),
            text = stringResource(R.string.nutrition_info_call),
            onClick = { onSetNutritionCard(true) }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
@Preview()
private fun HomeScreenWithoutNutritionPreview() {
    HomeScreenManager(
        modifier = Modifier,
        workouts = Constants().getNewTrainingAndSubGroupsHomeScreenMock(),
        listOfDays = Constants().getListOfDays(),
        viewModel = TrainingViewModelFake(),
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        isHomeScreenV2 = true,
        showNutritionCard = false,
        onHomeScreenV2 = {},
        onSetNutritionCard = {},
    )
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
@Preview()
private fun HomeScreenWithNutritionPreview() {
    HomeScreenManager(
        modifier = Modifier,
        workouts = Constants().getNewTrainingAndSubGroupsHomeScreenMock(),
        listOfDays = Constants().getListOfDays(),
        viewModel = TrainingViewModelFake(),
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        isHomeScreenV2 = true,
        showNutritionCard = true,
        onHomeScreenV2 = {},
        onSetNutritionCard = {},
    )
}