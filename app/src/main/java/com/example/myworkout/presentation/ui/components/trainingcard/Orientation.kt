package com.example.myworkout.presentation.ui.components.trainingcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.utils.DEFAULT_PADDING

sealed interface Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Render(
        modifier: Modifier,
        colors: SelectableChipColors,
        listOfMuscleSubGroup: List<MuscleSubGroupModel>,
        onItemClick: (MuscleSubGroupModel) -> Unit,
        enabled: Boolean
    )
}

object Vertical : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(
        modifier: Modifier,
        colors: SelectableChipColors,
        listOfMuscleSubGroup: List<MuscleSubGroupModel>,
        onItemClick: (MuscleSubGroupModel) -> Unit,
        enabled: Boolean
    ) {
        SetColumn(
            modifier = modifier,
            colors = colors,
            listOfMuscleSubGroup = listOfMuscleSubGroup,
            onItemClick = onItemClick
        )
    }
}

object Horizontal : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(
        modifier: Modifier,
        colors: SelectableChipColors,
        listOfMuscleSubGroup: List<MuscleSubGroupModel>,
        onItemClick: (MuscleSubGroupModel) -> Unit,
        enabled: Boolean
    ) {
        SetRow(
            modifier = modifier,
            colors = colors,
            listOfMuscleSubGroup = listOfMuscleSubGroup,
            onItemClick = onItemClick
        )
    }
}

object Grid : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(
        modifier: Modifier,
        colors: SelectableChipColors,
        listOfMuscleSubGroup: List<MuscleSubGroupModel>,
        onItemClick: (MuscleSubGroupModel) -> Unit,
        enabled: Boolean
    ) {
        SetGrid(
            modifier = modifier,
            colors = colors,
            listOfMuscleSubGroup = listOfMuscleSubGroup,
            onItemClick = onItemClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetColumn(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)
    ) {
        listOfMuscleSubGroup.forEach { item ->
            FilterChip(modifier, colors, item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetRow(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
        listOfMuscleSubGroup.forEach { item ->
            FilterChip(modifier, colors, item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
private fun SetGrid(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        listOfMuscleSubGroup.forEach { item ->
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
    FilterChip(
        modifier = modifier,
        colors = colors,
        onClick = { onItemClick(item) },
        content = {
            Text(
                color = colorResource(R.color.white),
                fontSize = 14.sp,
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
        },
        selected = item.selected
    )
}