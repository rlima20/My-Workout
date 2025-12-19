package com.example.myworkout.presentation.ui.components.home

import android.os.Build
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
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT
import com.example.myworkout.R
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.homeScreenCardPaddings
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.ui.components.commons.ToggleItem
import com.example.myworkout.presentation.ui.components.trainingcard.LabelTrainingCard
import com.example.myworkout.presentation.ui.components.trainingcard.TrainingCard
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModelFake
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModelFake

@RequiresApi(35)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    viewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>,
    trainingCardProps: TrainingCardProps
) {

    LazyVerticalGrid(
        modifier = trainingCardProps.modifier
            .homeScreenCardPaddings()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
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
                    trainingCardProps = trainingCardProps,
                    training = workouts[index].first,
                    subGroups = workouts[index].second,
                    listOfDays = listOfDays,
                    onUpdateTraining = { viewModel.updateTraining(it) },
                    onUpdateTrainingName = { viewModel.updateTrainingName(it) },
                    onDeleteTraining = { viewModel.deleteTraining(it) },
                    onClearSubGroups = { muscleGroupViewModel.unselectSubgroups(it) },
                    onUpdateSubGroup = {
                        muscleGroupViewModel
                            .updateNewSubGroup(
                                it.copy(selected = !it.selected)
                            )
                    },
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
@Preview
private fun HomeScreenPreviewFirst() {
    HomeScreen(
        modifier = Modifier,
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        viewModel = TrainingViewModelFake(),
        listOfDays = Constants().getListOfDays(),
        workouts = Constants().getNewTrainingAndSubGroupsHomeScreenMock(),
        trainingCardProps = TrainingCardProps(
            modifier = Modifier,
            topBarHeight = TRAINING_NAME_MAX_HEIGHT,
            chipHeight = 30.dp,
            cardHeight = 350.dp,
            trainingNameFontSize = 12.sp
        )
    )
}


@RequiresApi(35)
@Composable
@Preview
fun HomeScreenPreviewSecond() {
    HomeScreen(
        modifier = Modifier,
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        viewModel = TrainingViewModelFake(),
        listOfDays = Constants().getListOfDays(),
        workouts = Constants().getNewTrainingAndSubGroupsHomeScreenMock(),
        trainingCardProps = TrainingCardProps(
            modifier = Modifier,
            topBarHeight = 50.dp,
            chipHeight = 50.dp,
            cardHeight = 350.dp,
            trainingNameFontSize = 12.sp
        ),
    )
}