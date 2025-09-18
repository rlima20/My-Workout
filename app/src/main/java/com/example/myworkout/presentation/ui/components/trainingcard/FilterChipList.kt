package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SelectableChipColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.utils.DEFAULT_PADDING
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ResourceAsColor")
@Composable
internal fun FilterChipList(
    modifier: Modifier = Modifier,
    muscleSubGroups: List<MuscleSubGroupModel>,
    enabled: Boolean = true,
    colors: SelectableChipColors,
    orientation: Orientation,
    backGroundColor: Int,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    val modifier = modifier.background(colorResource(backGroundColor))

    orientation.Render(
        modifier = modifier,
        colors = colors,
        enabled = enabled,
        listOfMuscleSubGroup = muscleSubGroups,
        onItemClick = onItemClick
    )
}

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
            orientation = Vertical,
            muscleSubGroups = listOfMuscleSubGroup,
            colors = Utils().selectableChipColors(),
            enabled = false,
            backGroundColor = R.color.white,
            onItemClick = {}
        )

        FilterChipList(
            muscleSubGroups = listOfMuscleSubGroup,
            enabled = false,
            colors = Utils().selectableChipColors(),
            onItemClick = {},
            backGroundColor = R.color.white,
            orientation = Horizontal
        )

        FilterChipList(
            muscleSubGroups = listOfMuscleSubGroup,
            enabled = false,
            colors = Utils().selectableChipColors(),
            onItemClick = {},
            backGroundColor = R.color.white,
            orientation = Grid
        )
    }
}