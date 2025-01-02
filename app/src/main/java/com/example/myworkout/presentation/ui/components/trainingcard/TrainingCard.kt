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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import com.example.myworkout.data.model.MuscleSubGroup
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training

@RequiresApi(35)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    training: Training,
    isFilterChipListEnabled: Boolean,
    onAddButtonClicked: () -> Unit,
    onMuscleGroupSelected: (itemsSelected: MutableList<MuscleSubGroup>) -> Unit
) {
    var trainingStatus by remember { mutableStateOf(training.status) }
    val firstStatus by remember { mutableStateOf(training.status) }
    var isTrainingChecked by remember { mutableStateOf(training.status == Status.ACHIEVED) }

    var muscleSubGroupsState: MutableList<MuscleSubGroup> by remember {
        mutableStateOf(
            training.muscleGroups.flatMap { muscleGroup ->
                muscleGroup.muscleSubGroups
            }.toMutableList()
        )
    }

    Card(
        modifier = modifier.size(150.dp, 150.dp),
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
                listOfMuscleSubGroup = muscleSubGroupsState,
                onItemClick = { item ->
                    val muscleSubGroupsSelected: MutableList<MuscleSubGroup> = mutableListOf()

                    // Lista de Sub grupo de musculos
                    muscleSubGroupsState = muscleSubGroupsState.map { muscleSubGroup ->
                        if (muscleSubGroup.id == item.id) item.copy(selected = !item.selected)
                        else muscleSubGroup
                    }.toMutableList()

                    // Lógica para mostrar itens selecionados
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

private fun setStatus(
    isTrainingChecked: Boolean,
    trainingStatus: Status,
    firstStatus: Status
) = if (isTrainingChecked) {
    Status.ACHIEVED
} else {
    when (trainingStatus) {
        Status.MISSED -> {
            firstStatus
        }

        Status.EMPTY -> {
            Status.EMPTY
        }

        else -> {
            Status.PENDING
        }
    }
}

@Composable
private fun TrainingCheckbox(
    status: Status,
    isTrainingChecked: Boolean,
    onChecked: () -> Unit,
) {
    if (status != Status.EMPTY) {
        Checkbox(
            modifier = Modifier.offset(x = 104.dp, y = (-22).dp),
            checked = isTrainingChecked,
            onCheckedChange = { onChecked() },
        )
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
            .background(
                color = colorResource(setBackGroundColor(status))
            )
            .height(30.dp)
            .fillMaxWidth(),
    ) {
        if (status != Status.EMPTY) Text(trainingName)
    }
}

@Composable
private fun MuscleSubGroupSection(
    training: Training,
    listOfMuscleSubGroup: List<MuscleSubGroup>,
    isFilterChipListEnabled: Boolean = false,
    onItemClick: (item: MuscleSubGroup) -> Unit,
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

@Composable
private fun setBackGroundColor(status: Status): Int =
    when (status) {
        Status.PENDING -> R.color.pending
        Status.ACHIEVED -> R.color.achieved
        Status.MISSED -> R.color.missed
        Status.EMPTY -> R.color.empty
    }

@RequiresApi(35)
@Preview
@Composable
fun TrainingCardPreview() {
    Column {
        Status.entries.forEach {
            TrainingCard(
                modifier = Modifier,
                training = Constants().trainingMock(it),
                isFilterChipListEnabled = false,
                onMuscleGroupSelected = {},
                onAddButtonClicked = {}
            )
        }
    }
}