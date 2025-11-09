package com.example.myworkout.presentation.ui.components.home

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.LAZY_VERTICAL_GRID_MIN_SIZE
import com.example.myworkout.Constants.Companion.LAZY_VERTICAL_GRID_SPACING
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.homeScreenCardPaddings
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.components.trainingcard.LabelTrainingCard
import com.example.myworkout.presentation.ui.components.trainingcard.TrainingCard

@RequiresApi(35)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    dayOfWeek: String,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    workouts: List<Pair<TrainingModel, List<MuscleSubGroupModel>>>,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onUpdateDayOfWeek: (value: String) -> Unit,
    onUpdateTrainingName: (value: String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .homeScreenCardPaddings()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.spacedBy(LAZY_VERTICAL_GRID_SPACING),
        columns = GridCells.Adaptive(LAZY_VERTICAL_GRID_MIN_SIZE)
    ) {
        items(workouts.size) { index ->
            Column {
                LabelTrainingCard(
                    text = workouts[index].first.dayOfWeek.toPortugueseString(),
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = colorResource(R.color.title_color),
                    fontSize = 20.sp
                )
                TrainingCard(
                    filterChipListModifier = modifier,
                    training = workouts[index].first,
                    dayOfWeek = dayOfWeek,
                    subGroups = workouts[index].second,
                    listOfDays = listOfDays,
                    onUpdateTraining = { onTrainingChecked(it) },
                    onUpdateDayOfWeek = { onUpdateDayOfWeek(it) },
                    onUpdateTrainingName = { onUpdateTrainingName(it) },
                )
            }
        }
    }
}

@RequiresApi(35)
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        modifier = Modifier,
        dayOfWeek = "DOMINGO",
        listOfDays = Constants().getListOfDays(),
        trainingName = "",
        workouts = Constants().getTrainingAndSubGroupsHomeScreenMock(),
        onTrainingChecked = {},
        onUpdateDayOfWeek = {},
        onUpdateTrainingName = {},
    )
}