package com.example.myworkout.presentation.ui.components.home.homev2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.myworkout.presentation.ui.components.commons.ScrollableTextCard
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
    var myNotes by remember { mutableStateOf(workout.first.myNotes) }
    val scrollState = rememberScrollState()

    with(workout.first.myNotes) {
        LaunchedEffect(this) {
            myNotes = workout.first.myNotes
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            LabelTrainingCard(
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.title_color),
                maxLines = 1,
                text = workout.first.dayOfWeek.toPortugueseString(),
            )
        }

        TrainingCard(
            modifier = Modifier,
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

        ScrollableTextCard(
            modifier = Modifier.height(80.dp),
            text = myNotes,
            onTextChange = { myNotes = it },
            onFocus = { },
            onOkClick = {
                viewModel.updateTraining(
                    TrainingModel(
                        trainingId = workout.first.trainingId,
                        status = workout.first.status,
                        dayOfWeek = workout.first.dayOfWeek,
                        trainingName = workout.first.trainingName,
                        isChecked = workout.first.isChecked,
                        myNotes = myNotes,
                    )
                )
            }
        )
    }
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