package com.example.myworkout.presentation.ui.components.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import com.example.myworkout.extensions.toDayOfWeekOrNull
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.commons.DropdownItem
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.presentation.ui.components.trainingcard.Grid
import com.example.myworkout.presentation.ui.components.trainingcard.GridProps
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewTraining(
    subgroupsSelected: List<MuscleSubGroupModel>,
    groupsWithRelations: List<MuscleGroupModel>,
    selectedGroup: MuscleGroupModel,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    trainingsQuantity: Int,
    onFetchRelations: () -> Unit,
    onSetSelectedGroup: (MuscleGroupModel) -> Unit,
    onSaveTraining: (TrainingModel, MuscleGroupModel) -> Unit,
) {
    onFetchRelations()
    val maxDaysQuantity = 6
    val firstAvailableDay = listOfDays.firstOrNull { !it.second }?.first ?: DayOfWeek.values().first()
    var dayOfWeek by remember { mutableStateOf(firstAvailableDay.toPortugueseString()) }
    var enabled by remember { mutableStateOf(true) }
    var trainingName by remember { mutableStateOf(String()) }
    val focusRequester = remember { FocusRequester() }
    var firstTimeScreenOpenedListener by remember { mutableStateOf(true) }
    if (firstTimeScreenOpenedListener) onSetSelectedGroup(groupsWithRelations.first())

    Column(Modifier.fillMaxSize()) {
        TabRowComponent(
            muscleGroups = groupsWithRelations,
            onItemSelected = {
                firstTimeScreenOpenedListener = false
                onSetSelectedGroup(it)
            },
        )
        ButtonSection(
            modifier = Modifier.padding(top = 8.dp, bottom = 78.dp).fillMaxHeight(),
            titleSection = stringResource(R.string.subgroups),
            buttonName = stringResource(R.string.button_section_save_button),
            buttonEnabled = trainingName.isNotEmpty(),
            onButtonClick = {
                onSaveTraining(
                    TrainingModel(
                        status = Status.PENDING,
                        dayOfWeek = dayOfWeek.toDayOfWeekOrNull()!!,
                        trainingName = trainingName
                    ),
                    selectedGroup
                )
            },
            content = {
                TextFieldSection(
                    modifier = Modifier.padding(bottom = 8.dp),
                    focusRequester = focusRequester,
                    trainingName = trainingName,
                    enabled = enabled && trainingsQuantity <= maxDaysQuantity,
                    onValueChanged = {
                        trainingName = it
                        enabled = it.isNotEmpty()
                    }
                )
                FilterChipList(
                    backGroundColor = R.color.white,
                    orientation = Grid,
                    orientationProps = GridProps(
                        colors = Utils().selectableChipColors(),
                        listOfMuscleSubGroup = subgroupsSelected,
                        enabled = false,
                        onItemClick = {},
                        horizontalSpacedBy = 8.dp,
                        verticalSpacedBy = 1.dp,
                    ),
                )
                DropdownItem(
                    items = listOfDays,
                    text = dayOfWeek,
                    enabled = enabled && trainingsQuantity <= maxDaysQuantity,
                    onItemClick = { dayOfWeek = it }
                )
                Tooltip(
                    text = stringResource(R.string.all_days_used),
                    enabled = trainingsQuantity > maxDaysQuantity
                )
            }
        )
    }
}

// Todo - passar para outro arquivo
@Composable
fun Tooltip(
    text: String,
    enabled: Boolean = true,
    icon: Painter? = painterResource(R.drawable.baseline_warning_24),
    modifier: Modifier = Modifier
) {
    if (enabled) {
        Box(
            modifier = modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .background(colorResource(R.color.warning))
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        contentDescription = "",
                        painter = icon
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = text,
                    color = colorResource(R.color.text_color)
                )
            }
        }
    }
}

@Composable
private fun TextFieldSection(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    enabled: Boolean,
    trainingName: String,
    onValueChanged: (trainingName: String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = trainingName,
        singleLine = true,
        onValueChange = { onValueChanged(it) },
        enabled = enabled,
        label = {
            val label =
                if (trainingName.isEmpty()) stringResource(R.string.new_training_input_text_label)
                else Constants().emptyString()
            Text(
                text = label,
                color = colorResource(R.color.title_color),
                fontSize = 16.sp
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = colorResource(R.color.text_color),
            cursorColor = colorResource(R.color.text_color),
            backgroundColor = colorResource(R.color.text_field_background_color),
            textColor = colorResource(R.color.text_color),
            focusedIndicatorColor = colorResource(R.color.title_color)
        )
    )
}
//
//@Preview
//@Composable
//private fun NewTrainingPreview() {
//    val constants = Constants()
//    NewTraining(
//        workouts = constants,
//        subgroupsSelected = constants.subGroupsMock,
//        groupsWithRelations = constants.groupsMock,
//        selectedGroup = constants.groupsMock.first(),
//        listOfDays = emptyList(),
//        onFetchRelations = {},
//        onSetSelectedGroup = {},
//        onSaveTraining = { } as (TrainingModel, MuscleGroupModel) -> Unit,
//    )
//}

@Preview
@Composable
private fun ToolTipPreview() {
    Tooltip(
        text = "Você já possui treinamentos em todos os dias da semana. Exclua algum treino para continuar."
    )
}