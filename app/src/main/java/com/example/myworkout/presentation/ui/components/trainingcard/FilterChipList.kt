package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.Orientation

@SuppressLint("ResourceAsColor")
@Composable
internal fun FilterChipList(
    modifier: Modifier = Modifier,
    muscleSubGroups: List<MuscleSubGroupModel>,
    enabled: Boolean = true,
    orientation: Orientation = Orientation.VERTICAL,
    backGroundColor: Int = DEFAULT_COLOR,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
    ) {
    val modifier = modifier
        .background(colorResource(backGroundColor))

    SetFilterChipListOrientation(
        orientation = orientation,
        modifier = modifier,
        listOfMuscleSubGroup = muscleSubGroups,
        onItemClick = onItemClick
    )
}

@Composable
private fun SetFilterChipListOrientation(
    orientation: Orientation,
    modifier: Modifier,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    when (orientation) {
        Orientation.VERTICAL -> {
            SetLazyColumn(
                modifier = modifier,
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                onItemClick = onItemClick
            )
        }

        Orientation.HORIZONTAL -> {
            SetLazyRow(
                modifier = modifier,
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun SetLazyRow(
    modifier: Modifier,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    LazyRow(
        //modifier = modifier.background(color = colorResource(R.color.empty)),
        horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)
    ) {
        items(listOfMuscleSubGroup) { item ->
            FilterChip(modifier, onItemClick, item)
        }
    }
}

@Composable
private fun SetLazyColumn(
    modifier: Modifier,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)
    ) {
        items(listOfMuscleSubGroup) { item ->
            FilterChip(modifier, onItemClick, item)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FilterChip(
    modifier: Modifier,
    onItemClick: (item: MuscleSubGroupModel) -> Unit,
    item: MuscleSubGroupModel
) {
    FilterChip(
        modifier = modifier.height(22.dp),
        onClick = { onItemClick(item) },
        label = { Text(fontSize = 16.sp, text = item.name) },
        selected = false
    )
}

const val DEFAULT_COLOR = R.color.empty_2
val DEFAULT_PADDING = 4.dp

@Preview
@Composable
fun AssistChipListPreview() {
    val listOfMuscleSubGroup: MutableList<MuscleSubGroupModel> = mutableListOf()
    for (i in 1..5) {
        listOfMuscleSubGroup.add(
            MuscleSubGroupModel(id = i, name = "Grupo$i")
        )
    }
    Column(
        modifier = Modifier.padding(DEFAULT_PADDING)
    ) {
        FilterChipList(
            muscleSubGroups = listOfMuscleSubGroup,
            enabled = false,
            onItemClick = {}
        )

        FilterChipList(
            muscleSubGroups = listOfMuscleSubGroup,
            enabled = false,
            onItemClick = {},
            orientation = Orientation.HORIZONTAL
        )
    }
}