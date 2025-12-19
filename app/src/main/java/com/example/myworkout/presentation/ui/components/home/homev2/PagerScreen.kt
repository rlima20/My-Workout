package com.example.myworkout.presentation.ui.components.home.homev2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.ui.components.trainingcard.LabelTrainingCard
import com.example.myworkout.presentation.ui.components.trainingcard.TrainingCard
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModelFake
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModelFake

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun PagerScreen(
    workout: Pair<TrainingModel, List<SubGroupModel>>,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    viewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel,
    trainingCardProps: TrainingCardProps
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LabelTrainingCard(
            text = workout.first.dayOfWeek.toPortugueseString(),
            modifier = Modifier.padding(bottom = 8.dp),
            color = colorResource(R.color.title_color),
            fontSize = 20.sp
        )
        TrainingCard(
            modifier = chooseModifier(trainingCardProps, Modifier),
            training = workout.first,
            subGroups = workout.second,
            listOfDays = listOfDays,
            trainingCardProps = trainingCardProps,
            onUpdateTraining = { viewModel.updateTraining(it) },
            onUpdateTrainingName = { viewModel.updateTrainingName(it) },
            onDeleteTraining = { viewModel.deleteTraining(it) },
            onClearSubGroups = { muscleGroupViewModel.unselectSubgroups(it) },
            onUpdateSubGroup = {
                muscleGroupViewModel
                    .updateNewSubGroup(
                        it.copy(selected = !it.selected)
                    )
            }
        )
    }
}

@Composable
private fun chooseModifier(
    trainingCardProps: TrainingCardProps,
    modifier: Modifier
): Modifier {
    val innerModifier = if (trainingCardProps.cardHeight != null) {
        modifier.height(trainingCardProps.cardHeight)
    } else {
        modifier
    }
    return innerModifier
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
@Preview
private fun HomeScreenPreview() {
    PagerScreen(
        workout = Constants().getNewTrainingAndSubGroupsHomeScreenMock().first(),
        listOfDays = Constants().getListOfDays(),
        viewModel = TrainingViewModelFake(),
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        trainingCardProps = TrainingCardProps(
            modifier = Modifier,
            topBarHeight = 50.dp,
            chipHeight = 50.dp,
            cardHeight = 350.dp,
            trainingNameFontSize = 12.sp
        )
    )
}