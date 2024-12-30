package com.example.myworkout.presentation.ui.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.data.model.MuscleGroup
import com.example.myworkout.data.model.MuscleSubGroup
import com.example.myworkout.data.model.Status
import com.example.myworkout.data.model.Training
import java.util.function.UnaryOperator

@RequiresApi(35)
@SuppressLint(
    "UnrememberedMutableState",
    "MutableCollectionMutableState"
)
@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    training: Training,
) {
    var isTrainingChecked by remember {
        mutableStateOf(training.status == Status.ACHIEVED)
    }

    val listOfMuscleSubGroup: MutableList<MuscleSubGroup> = mutableListOf()

    training.muscleGroups.forEach { muscleGroup ->
        muscleGroup.muscleSubGroups.forEach { muscleSubGroup ->
            listOfMuscleSubGroup.add(muscleSubGroup)
        }
    }

    var muscleSubGroups: MutableList<MuscleSubGroup> by remember { mutableStateOf(listOfMuscleSubGroup) }

    Card(
        modifier = modifier.size(150.dp, 150.dp),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            MuscleGroupSection(training)
            MuscleSubGroupSection(
                training = training,
                listOfMuscleSubGroup = muscleSubGroups,
                onItemClick = { item ->
                    muscleSubGroups = muscleSubGroups.map { muscleSubGroup ->
                        if (muscleSubGroup.id == item.id) {
                            // Retorna o item atualizado
                            item.copy(selected = !item.selected) // Alternando o estado 'selected'
                        } else {
                            // Retorna o item original
                            muscleSubGroup
                        }
                    }.toMutableList()
                }
            )
            TrainingCheckbox(
                training = training,
                isTrainingChecked = isTrainingChecked,
                onChecked = { isTrainingChecked = it }
            )
        }
    }
}

//onItemClick = { item ->
//    muscleSubGroups.forEach { muscleSubGroup ->
//        if (muscleSubGroup.id == item.id) {
//            val position = item.
//            val selected = muscleSubGroups.get(item.id item.id).selected
//            muscleSubGroups[item.id].selected = !selected
//        }
//    }
//    // Alterar o stado de selected do item e comparar os ids para confirmar qual deverá ser alterado.


@Composable
private fun TrainingCheckbox(
    training: Training,
    isTrainingChecked: Boolean,
    onChecked: (checked: Boolean) -> Unit
) {
    if (training.status != Status.EMPTY) {
        Checkbox(
            modifier = Modifier.offset(x = 104.dp, y = (-22).dp),
            checked = isTrainingChecked,
            onCheckedChange = { onChecked(!isTrainingChecked) },
        )
    }
}

@Composable
private fun MuscleGroupSection(training: Training) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = colorResource(setBackGroundColor(training.status))
            )
            .height(30.dp)
            .fillMaxWidth(),
    ) {
        if (training.status != Status.EMPTY) Text(training.trainingName)
    }
}

@Composable
private fun MuscleSubGroupSection(
    training: Training,
    listOfMuscleSubGroup: List<MuscleSubGroup>,
    onItemClick: (item: MuscleSubGroup) -> Unit
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
            )
        } else AddTrainingIconButton()
    }
}

fun getAllMuscleSubGroups(listOfMuscleGroup: List<MuscleGroup>): List<MuscleSubGroup> {
    val listOfMuscleSubGroup: MutableList<MuscleSubGroup> = mutableListOf()
    listOfMuscleGroup.forEach { muscleGroup ->
        muscleGroup.muscleSubGroups.forEach { muscleSubGroup ->
            listOfMuscleSubGroup.add(muscleSubGroup)
        }
    }
    return listOfMuscleSubGroup
}

@Composable
fun AddTrainingIconButton() {
    IconButton(onClick = {}) {
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Default.AddCircle,
            contentDescription = null,
        )
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
                training = Constants().trainingMock(it),
            )
        }
    }
}

@Composable
fun FilterChipList(
    listOfMuscleSubGroup: List<MuscleSubGroup>,
    onItemClick: (item: MuscleSubGroup) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .background(colorResource(R.color.empty)),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(listOfMuscleSubGroup) { item ->
            FilterChip(
                modifier = Modifier.height(18.dp),
                onClick = { onItemClick(item) },
                label = { Text(fontSize = 8.sp, text = item.name) },
                selected = item.selected
            )
        }
    }
}

@Preview
@Composable
fun AssistChipListPreview() {
    val listOfMuscleSubGroup: MutableList<MuscleSubGroup> = mutableListOf()
    for (i in 1..5) {
        listOfMuscleSubGroup.add(
            MuscleSubGroup(id = i, name = "Grupo$i")
        )
    }
    FilterChipList(
        listOfMuscleSubGroup = listOfMuscleSubGroup,
        onItemClick = {}
    )
}