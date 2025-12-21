package com.example.myworkout.presentation.ui.components.home.homev2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.ui.components.commons.Divider
import com.example.myworkout.presentation.ui.components.commons.FabSection
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
    var editTextFocused by remember { mutableStateOf(false) }

    with(workout.first.myNotes) {
        LaunchedEffect(this) {
            myNotes = workout.first.myNotes
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp),
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

        Divider()

        ScrollableTextCard(
            modifier = Modifier.height(120.dp),
            text = myNotes,
            onTextChange = { myNotes = it },
            onFocus = { editTextFocused = it }
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            FabSection(
                buttonName = stringResource(R.string.button_section_save_button),
                enabled = editTextFocused,
                onClick = {
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
                    editTextFocused = false
                },
            )
        }
    }
}

@Composable
fun ScrollableTextCard(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Digite aqui...",
    minHeight: Dp = 150.dp,
    onFocus: (focus: Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }

    Column {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.title_color),
            maxLines = 1,
            text = stringResource(R.string.my_notes)
        )

        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .padding(12.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState -> onFocus(focusState.isFocused) },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                text = hint,
                                color = colorResource(R.color.title_color)
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
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