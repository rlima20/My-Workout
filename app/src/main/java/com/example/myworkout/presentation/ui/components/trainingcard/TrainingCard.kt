package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.Status
import com.example.myworkout.extensions.setBackGroundColor
import com.example.myworkout.utils.setStatus

@RequiresApi(35)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    training: TrainingModel,
    muscleSubGroupModel: List<MuscleSubGroupModel>,
    isFilterChipListEnabled: Boolean,
    onAddButtonClicked: () -> Unit,
    onMuscleGroupSelected: (itemsSelected: MutableList<MuscleSubGroupModel>) -> Unit
) {
    var trainingStatus by remember { mutableStateOf(training.status) }
    val firstStatus by remember { mutableStateOf(training.status) }
    var isTrainingChecked by remember { mutableStateOf(training.status == Status.ACHIEVED) }

    var muscleSubGroupsState = muscleSubGroupModel

    Card(
        modifier = modifier,
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            MuscleGroupSection(
                trainingName = training.trainingName,
                status = trainingStatus
            )
            MuscleSubGroupSection(
                training = training,
                listOfMuscleSubGroup = muscleSubGroupModel,
                onItemClick = { item ->
                    val muscleSubGroupsSelected: MutableList<MuscleSubGroupModel> = mutableListOf()

                    muscleSubGroupsState = muscleSubGroupsState.map { muscleSubGroup ->
                        if (muscleSubGroup.id == item.id) item.copy(selected = !item.selected)
                        else muscleSubGroup
                    }.toMutableList()

                    if (!item.selected) muscleSubGroupsSelected.remove(item)
                    else muscleSubGroupsSelected.add(item.copy(selected = true))

                    onMuscleGroupSelected(muscleSubGroupsSelected)
                },
                onAddButtonClicked = { onAddButtonClicked() },
                isFilterChipListEnabled = isFilterChipListEnabled
            )
            TrainingCheckbox(
                status = trainingStatus,
                isTrainingChecked = isTrainingChecked,
                onChecked = {
                    isTrainingChecked = !isTrainingChecked
                    trainingStatus = setStatus(isTrainingChecked, trainingStatus, firstStatus)
                },
            )
        }
    }
}


@Composable
private fun MuscleGroupSection(
    trainingName: String,
    status: Status
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = colorResource(status.setBackGroundColor()))
            .height(30.dp)
            .fillMaxWidth(),
    ) {
        if (status != Status.EMPTY) Text(trainingName)
    }
}

@Composable
private fun MuscleSubGroupSection(
    training: TrainingModel,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    isFilterChipListEnabled: Boolean = false,
    onItemClick: (item: MuscleSubGroupModel) -> Unit,
    onAddButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = colorResource(R.color.content))
            .fillMaxSize()
            .background(colorResource(R.color.empty))
    ) {
        if (training.status != Status.EMPTY) {
            FilterChipList(
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                onItemClick = { onItemClick(it) },
                enabled = isFilterChipListEnabled
            )
        } else AddTrainingIconButton {
            onAddButtonClicked()
        }
    }
}

@RequiresApi(35)
@Preview
@Composable
fun TrainingCardPreview() {
    Column {
        Status.values().forEach {
            TrainingCard(
                modifier = Modifier,
                training = Constants().trainingMock(it),
                muscleSubGroupModel = listOf(),
                isFilterChipListEnabled = false,
                onMuscleGroupSelected = {},
                onAddButtonClicked = {}
            )
        }
    }
}