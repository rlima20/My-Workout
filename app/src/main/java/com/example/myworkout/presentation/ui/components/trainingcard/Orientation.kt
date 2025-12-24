package com.example.myworkout.presentation.ui.components.trainingcard

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.presentation.ui.components.commons.Action
import com.example.myworkout.presentation.ui.components.commons.CustomSelectableChip
import com.example.myworkout.presentation.ui.components.commons.TextFieldComponent
import com.example.myworkout.utils.Utils

sealed interface Orientation {
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

data class GridTrainingProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val colors: SelectableChipColors,
    val listOfMuscleGroup: List<MuscleGroupModel>,
    val enabled: Boolean = true,
    val horizontalSpacedBy: Dp,
    val verticalSpacedBy: Dp,
    val onItemClick: (MuscleGroupModel) -> Unit = {}
) : OrientationProps

data class GridMuscleGroupProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val objSelected: Pair<Int, Boolean>,
    val showDialog: Boolean,
    val listOfMuscleGroup: List<MuscleGroupModel>,
    val onItemClick: (MuscleGroupModel) -> Unit,
    val onConfirm: (MuscleGroupModel) -> Unit,
    val onDeleteGroup: (MuscleGroupModel) -> Unit,
    val onShowDialog: (Boolean, Action) -> Unit,
) : OrientationProps

data class GridMuscleSubGroupProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val showDialog: Boolean,
    val listOfSubGroup: List<MuscleSubGroupModel>,
    val onItemClick: (MuscleSubGroupModel) -> Unit,
    val onConfirm: (MuscleSubGroupModel) -> Unit,
    val onDeleteSubGroup: (MuscleSubGroupModel) -> Unit,
    val onShowDialog: (Boolean, Action) -> Unit,
) : OrientationProps

data class HomeGridProps @OptIn(ExperimentalMaterialApi::class) constructor(
    val chipHeight: Dp,
    val colors: SelectableChipColors,
    val listOfMuscleSubGroup: List<SubGroupModel>,
    val enabled: Boolean = true,
    val horizontalSpacedBy: Dp,
    val verticalSpacedBy: Dp,
    val onItemClick: (SubGroupModel) -> Unit = {}
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

object GridTraining : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(modifier: Modifier, props: OrientationProps) {
        props as GridTrainingProps
        SetGridTraining(
            modifier = modifier,
            colors = props.colors,
            listOfMuscleGroup = props.listOfMuscleGroup,
            horizontalSpacedBy = props.horizontalSpacedBy,
            verticalSpacedBy = props.verticalSpacedBy,
            onItemClick = props.onItemClick
        )
    }
}

object GridMuscleGroup : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(modifier: Modifier, props: OrientationProps) {
        props as GridMuscleGroupProps

        SetGridMuscleGroup(
            modifier = modifier,
            listOfMuscleGroup = props.listOfMuscleGroup,
            objSelected = props.objSelected,
            showDialog = props.showDialog,
            onItemClick = props.onItemClick,
            onConfirm = props.onConfirm,
            onDeleteGroup = props.onDeleteGroup,
            onShowDialog = props.onShowDialog
        )
    }
}

object GridSubGroup : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(modifier: Modifier, props: OrientationProps) {
        props as GridMuscleSubGroupProps

        SetGridSubGroup(
            modifier = modifier,
            listOfSubGroup = props.listOfSubGroup,
            showDialog = props.showDialog,
            onItemClick = { props.onItemClick },
            onConfirm = { props.onConfirm },
            onDeleteSubGroup = { props.onDeleteSubGroup },
            onShowDialog = { value, action -> props.onShowDialog(value, action) },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun SetGridSubGroup(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    listOfSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (MuscleSubGroupModel) -> Unit,
    onConfirm: (MuscleSubGroupModel) -> Unit,
    onDeleteSubGroup: (MuscleSubGroupModel) -> Unit,
    onShowDialog: (Boolean, Action) -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        listOfSubGroup.forEach { subgroup ->
            val interactionSource = remember { MutableInteractionSource() }
            val focusRequester = remember { FocusRequester() }
            var name by remember(subgroup.name, showDialog) {
                mutableStateOf(subgroup.name)
            }

            CustomSelectableChip(
                modifier = Modifier,
                text = subgroup.name,
                selected = subgroup.selected,
                enabled = true,
                chipHeight = 36.dp,
                textSize = 16.sp,
                interactionSource = interactionSource,
                onClick = { onItemClick(subgroup) },
                onLongClick = {
                    onShowDialog(
                        true,
                        Action.Edit(
                            title = R.string.edit_group,
                            onConfirm = {
                                onConfirm(
                                    subgroup.copy(name = name)
                                )
                            },
                            content = {
                                TextFieldComponent(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    text = name,
                                    isSingleLine = true,
                                    focusRequester = focusRequester,
                                    onValueChange = { name = it },
                                    enabled = true,
                                    label = {
                                        Text(
                                            text = stringResource(R.string.training_name_string),
                                            color = colorResource(R.color.title_color),
                                            fontSize = 16.sp
                                        )
                                    }
                                )
                            }
                        )
                    )
                },
                onDoubledClick = {
                    onShowDialog(
                        true,
                        Action.Delete(
                            title = R.string.delete_group,
                            onConfirm = { onDeleteSubGroup(subgroup) },
                            content = { Text("Deseja deletar esse subgrupo?") }
                        )
                    )
                }
            )
        }
    }
}

object HomeGrid : Orientation {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Render(
        modifier: Modifier,
        props: OrientationProps
    ) {
        props as HomeGridProps
        SetHomeGrid(
            modifier = modifier,
            chipHeight = props.chipHeight,
            colors = props.colors,
            isEnabled = props.enabled,
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
        modifier = modifier.fillMaxWidth(),
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
        modifier = modifier.fillMaxWidth(),
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
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacedBy),
        verticalArrangement = Arrangement.spacedBy(verticalSpacedBy)
    ) {
        listOfMuscleSubGroup.forEach { item ->
            FilterChip(colors, item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
private fun SetGridTraining(
    modifier: Modifier,
    colors: SelectableChipColors,
    listOfMuscleGroup: List<MuscleGroupModel>,
    horizontalSpacedBy: Dp,
    verticalSpacedBy: Dp,
    onItemClick: (item: MuscleGroupModel) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacedBy),
        verticalArrangement = Arrangement.spacedBy(verticalSpacedBy)
    ) {
        listOfMuscleGroup.forEach { item ->
            FilterChipTraining(
                modifier = modifier,
                colors = colors,
                item = item,
                onItemClick = { onItemClick(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun SetGridMuscleGroup(
    modifier: Modifier = Modifier,
    objSelected: Pair<Int, Boolean>,
    showDialog: Boolean,
    listOfMuscleGroup: List<MuscleGroupModel>,
    onItemClick: (MuscleGroupModel) -> Unit,
    onConfirm: (MuscleGroupModel) -> Unit,
    onDeleteGroup: (MuscleGroupModel) -> Unit,
    onShowDialog: (Boolean, Action) -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val utils = Utils()
        listOfMuscleGroup.forEach { muscleGroup ->

            val interactionSource = remember { MutableInteractionSource() }
            val focusRequester = remember { FocusRequester() }
            val selected = utils.setSelectedItem(objSelected, muscleGroup)

            // IMPORTANT: lembrar usando muscleGroup.name E showDialog como keys.
            // Assim, quando o diálogo abrir/fechar (showDialog mudar) ou o nome do grupo mudar,
            // o estado "name" será reinicializado com muscleGroup.name.
            var name by remember(muscleGroup.name, showDialog) {
                mutableStateOf(muscleGroup.name)
            }

            CustomSelectableChip(
                modifier = Modifier,
                text = muscleGroup.name,
                selected = selected,
                enabled = muscleGroup.enabled,
                interactionSource = interactionSource,
                onClick = { onItemClick(muscleGroup) },
                onLongClick = {
                    onShowDialog(
                        true,
                        Action.Edit(
                            title = R.string.edit_group,
                            onConfirm = {
                                onConfirm(
                                    muscleGroup.copy(name = name)
                                )
                            },
                            content = {
                                TextFieldComponent(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    text = name,
                                    isSingleLine = true,
                                    focusRequester = focusRequester,
                                    onValueChange = { name = it },
                                    enabled = true,
                                    label = {
                                        Text(
                                            text = stringResource(R.string.training_name_string),
                                            color = colorResource(R.color.title_color),
                                            fontSize = 16.sp
                                        )
                                    }
                                )
                            }
                        )
                    )
                },
                onDoubledClick = {
                    onShowDialog(
                        true,
                        Action.Delete(
                            title = R.string.delete_group,
                            onConfirm = { onDeleteGroup(muscleGroup) },
                            content = { Text("Deseja deletar esse grupo?") }
                        )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
private fun SetHomeGrid(
    modifier: Modifier,
    chipHeight: Dp,
    colors: SelectableChipColors,
    isEnabled: Boolean,
    listOfMuscleSubGroup: List<SubGroupModel>,
    horizontalSpacedBy: Dp,
    verticalSpacedBy: Dp,
    onItemClick: (item: SubGroupModel) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacedBy),
        verticalArrangement = Arrangement.spacedBy(verticalSpacedBy)
    ) {
        listOfMuscleSubGroup.forEach { item ->
            HomeFilterChip(
                modifier = modifier.height(chipHeight),
                colors = colors,
                isEnabled = isEnabled,
                item = item,
                onItemClick = { onItemClick(it) }
            )
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

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun FilterChipTraining(
    modifier: Modifier = Modifier,
    colors: SelectableChipColors,
    item: MuscleGroupModel,
    onItemClick: (item: MuscleGroupModel) -> Unit,
) {
    FilterChip(
        modifier = modifier,
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

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun HomeFilterChip(
    modifier: Modifier,
    colors: SelectableChipColors,
    isEnabled: Boolean,
    item: SubGroupModel,
    onItemClick: (item: SubGroupModel) -> Unit,
) {
    FilterChip(
        modifier = modifier,
        colors = colors,
        selected = item.selected,
        enabled = true,
        onClick = { if (isEnabled) return@FilterChip onItemClick(item) },
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
    )
}