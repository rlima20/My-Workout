package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.DEFAULT_PADDING
import com.example.myworkout.Constants.Companion.SUB_GROUP_SECTION_BACKGROUND
import com.example.myworkout.Constants.Companion.TRAINING_CARD_PADDING_BOTTOM
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT
import com.example.myworkout.Constants.Companion.TRAINING_NAME_SHOULDER
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import com.example.myworkout.extensions.setBackGroundColor
import com.example.myworkout.extensions.trainingCardFilterChipListModifier
import com.example.myworkout.presentation.ui.components.commons.CheckBox
import com.example.myworkout.presentation.ui.components.commons.IconButton
import com.example.myworkout.utils.Utils

@RequiresApi(35)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    filterChipListModifier: Modifier = Modifier,
    training: TrainingModel,
    subGroups: List<MuscleSubGroupModel>,
    chipListEnabled: Boolean,
    onAddButtonClicked: () -> Unit,
    onMuscleGroupSelected: (itemsSelected: MutableList<MuscleSubGroupModel>) -> Unit,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onGetMuscleSubGroupsByTrainingId: (trainingId: Int) -> Unit
) {
    var trainingStatus by remember { mutableStateOf(training.status) }
    val firstStatus by remember { mutableStateOf(training.status) }
    var isTrainingChecked by remember { mutableStateOf(training.status == Status.ACHIEVED) }
    var subGroupsState = subGroups

    Card(
        modifier = modifier.padding(bottom = TRAINING_CARD_PADDING_BOTTOM),
        colors = Utils().buttonSectionCardsColors(),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SetTrainingName(
                trainingName = training.trainingName,
                status = trainingStatus
            )
            SetSubGroupSection(
                filterChipListModifier = filterChipListModifier,
                training = training,
                subGroups = subGroups,
                onItemClick = { item ->
                    val subGroupsSelected: MutableList<MuscleSubGroupModel> = mutableListOf()

                    subGroupsState = subGroupsState.map { muscleSubGroup ->
                        if (muscleSubGroup.id == item.id) item.copy(selected = !item.selected)
                        else muscleSubGroup
                    }.toMutableList()

                    if (!item.selected) subGroupsSelected.remove(item)
                    else subGroupsSelected.add(item.copy(selected = true))

                    onMuscleGroupSelected(subGroupsSelected)
                },
                onAddButtonClicked = { onAddButtonClicked() },
                chipListEnabled = chipListEnabled,
                onGetMuscleSubGroupsByTrainingId = { onGetMuscleSubGroupsByTrainingId(it) }
            )
            CheckBox(
                status = trainingStatus,
                isTrainingChecked = isTrainingChecked,
                enabled = trainingStatus != Status.MISSED && trainingStatus != Status.ACHIEVED,
                onChecked = {
                    isTrainingChecked = !isTrainingChecked
                    trainingStatus = Utils().setStatus(
                        isTrainingChecked,
                        trainingStatus,
                        firstStatus
                    )
                    onTrainingChecked(
                        TrainingModel(
                            trainingId = training.trainingId,
                            status = trainingStatus,
                            trainingName = training.trainingName,
                            dayOfWeek = training.dayOfWeek
                        )
                    )
                },
            )
        }
    }
}

@Composable
private fun SetTrainingName(
    trainingName: String,
    status: Status
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = colorResource(status.setBackGroundColor()))
            .height(TRAINING_NAME_MAX_HEIGHT)
            .fillMaxWidth(),
    ) {
        if (status != Status.EMPTY) Text(trainingName)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetSubGroupSection(
    filterChipListModifier: Modifier,
    training: TrainingModel,
    subGroups: List<MuscleSubGroupModel>,
    chipListEnabled: Boolean = false,
    onItemClick: (item: MuscleSubGroupModel) -> Unit,
    onAddButtonClicked: () -> Unit,
    onGetMuscleSubGroupsByTrainingId: (trainingId: Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = colorResource(SUB_GROUP_SECTION_BACKGROUND))
            .fillMaxWidth()
    ) {
        if (training.status != Status.EMPTY) {
            onGetMuscleSubGroupsByTrainingId(training.trainingId)

            FilterChipList(
                modifier = filterChipListModifier.trainingCardFilterChipListModifier(),
                backGroundColor = SUB_GROUP_SECTION_BACKGROUND,
                orientation = Grid,
                orientationProps = GridProps(
                    colors = Utils().selectableChipColors(),
                    listOfMuscleSubGroup = subGroups,
                    enabled = chipListEnabled,
                    horizontalSpacedBy = DEFAULT_PADDING,
                    verticalSpacedBy = DEFAULT_PADDING,
                    onItemClick = { onItemClick(it) }
                ),
            )
        } else IconButton(
            painter = painterResource(R.drawable.add_icon),
            onClick = { onAddButtonClicked() }
        )
    }
}

@RequiresApi(35)
@Preview
@Composable
fun TrainingCardPreview() {
    val constants = Constants()
    val shoulder = TRAINING_NAME_SHOULDER
    Column {
        Status.values().forEach {
            TrainingCard(
                modifier = Modifier.padding(bottom = 4.dp),
                training = constants.getTrainingMock(it, shoulder, DayOfWeek.MONDAY),
                subGroups = constants.subGroupsMock,
                chipListEnabled = false,
                onMuscleGroupSelected = {},
                onAddButtonClicked = {},
                onTrainingChecked = {},
                onGetMuscleSubGroupsByTrainingId = {}
            )
        }
    }
}