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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SelectableChipColors
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
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ResourceAsColor")
@Composable
internal fun FilterChipList(
    modifier: Modifier = Modifier,
    muscleSubGroups: List<MuscleSubGroupModel>,
    enabled: Boolean = true,
    colors: SelectableChipColors,
    orientation: Orientation = Orientation.VERTICAL,
    backGroundColor: Int = DEFAULT_COLOR,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    val modifier = modifier
        .background(colorResource(backGroundColor))

    SetFilterChipListOrientation(
        orientation = orientation,
        colors = colors,
        modifier = modifier,
        listOfMuscleSubGroup = muscleSubGroups,
        onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetFilterChipListOrientation(
    orientation: Orientation,
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    when (orientation) {
        Orientation.VERTICAL -> {
            SetLazyColumn(
                modifier = modifier,
                colors = colors,
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                onItemClick = onItemClick
            )
        }

        Orientation.HORIZONTAL -> {
            SetLazyRow(
                modifier = modifier,
                colors = colors,
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                onItemClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetLazyRow(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    LazyRow(
        //modifier = modifier.background(color = colorResource(R.color.empty)),
        horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)
    ) {
        items(listOfMuscleSubGroup) { item ->
            FilterChip(modifier, colors, item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetLazyColumn(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)
    ) {
        items(listOfMuscleSubGroup) { item ->
            FilterChip(modifier, colors, item, onItemClick)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun FilterChip(
    modifier: Modifier,
    colors: SelectableChipColors,
    item: MuscleSubGroupModel,
    onItemClick: (item: MuscleSubGroupModel) -> Unit,
) {
    androidx.compose.material.FilterChip(
        modifier = modifier.height(22.dp),
        colors = colors,
        onClick = { onItemClick(item) },
        content = {
            Text(
                color = colorResource(R.color.white),
                fontSize = 16.sp,
                text = item.name
            )
        },
        selected = item.selected
    )
}

const val DEFAULT_COLOR = R.color.empty_2
val DEFAULT_PADDING = 8.dp

@OptIn(ExperimentalMaterialApi::class)
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
            colors = Utils().selectableChipColors(),
            enabled = false,
            onItemClick = {}
        )

        FilterChipList(
            muscleSubGroups = listOfMuscleSubGroup,
            enabled = false,
            colors = Utils().selectableChipColors(),
            onItemClick = {},
            orientation = Orientation.HORIZONTAL
        )
    }
}