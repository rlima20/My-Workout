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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel

sealed interface Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Render(modifier: Modifier, props: OrientationProps)
}

// Interface criada para respeitar os princípios do solid: Não forçar uma implementação desnecessária no Vertical e no Horizontal
interface OrientationProps

data class VerticalProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val colors: SelectableChipColors,
    val listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    val enabled: Boolean = true,
    val verticalSpacedBy: Dp,
    val onItemClick: (MuscleSubGroupModel) -> Unit
) : OrientationProps

data class HorizontalProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val colors: SelectableChipColors,
    val listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    val enabled: Boolean = true,
    val horizontalSpacedBy: Dp,
    val onItemClick: (MuscleSubGroupModel) -> Unit
) : OrientationProps

data class GridProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val colors: SelectableChipColors,
    val listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    val enabled: Boolean = true,
    val horizontalSpacedBy: Dp,
    val verticalSpacedBy: Dp,
    val onItemClick: (MuscleSubGroupModel) -> Unit = {}
) : OrientationProps

object Vertical : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(modifier: Modifier, props: OrientationProps) {
        props as VerticalProps
        SetColumn(
            modifier = modifier,
            colors = props.colors,
            listOfMuscleSubGroup = props.listOfMuscleSubGroup,
            verticalSpacedBy = props.verticalSpacedBy,
            onItemClick = props.onItemClick
        )
    }
}

object Horizontal : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(modifier: Modifier, props: OrientationProps) {
        props as HorizontalProps
        SetRow(
            modifier = modifier,
            colors = props.colors,
            listOfMuscleSubGroup = props.listOfMuscleSubGroup,
            horizontalSpacedBy = props.horizontalSpacedBy,
            onItemClick = props.onItemClick
        )
    }
}

object Grid : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(modifier: Modifier, props: OrientationProps) {
        props as GridProps
        SetGrid(
            modifier = modifier,
            colors = props.colors,
            listOfMuscleSubGroup = props.listOfMuscleSubGroup,
            horizontalSpacedBy = props.horizontalSpacedBy,
            verticalSpacedBy = props.verticalSpacedBy,
            onItemClick = props.onItemClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetColumn(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    verticalSpacedBy: Dp,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpacedBy)
    ) {
        listOfMuscleSubGroup.forEach { item ->
            FilterChip(colors, item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetRow(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    horizontalSpacedBy: Dp,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacedBy)
    ) {
        listOfMuscleSubGroup.forEach { item ->
            FilterChip(colors, item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
private fun SetGrid(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    horizontalSpacedBy: Dp,
    verticalSpacedBy: Dp,
    onItemClick: (item: MuscleSubGroupModel) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacedBy),
        verticalArrangement = Arrangement.spacedBy(verticalSpacedBy)
    ) {
        listOfMuscleSubGroup.forEach { item ->
            FilterChip(colors, item, onItemClick)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun FilterChip(
    colors: SelectableChipColors,
    item: MuscleSubGroupModel,
    onItemClick: (item: MuscleSubGroupModel) -> Unit,
) {
    FilterChip(
        colors = colors,
        onClick = { onItemClick(item) },
        content = {
            Text(
                color = colorResource(if (item.selected) R.color.white else R.color.text_color),
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