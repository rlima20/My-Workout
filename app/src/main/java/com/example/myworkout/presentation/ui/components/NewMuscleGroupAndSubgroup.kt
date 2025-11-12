package com.example.myworkout.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.DEFAULT_PADDING
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.ui.components.commons.Action
import com.example.myworkout.presentation.ui.components.commons.ActionDialog
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.commons.CustomSelectableChip
import com.example.myworkout.presentation.ui.components.commons.FabSection
import com.example.myworkout.presentation.ui.components.commons.Label
import com.example.myworkout.presentation.ui.components.commons.TextFieldComponent
import com.example.myworkout.presentation.ui.components.musclegroup.ItemCard
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.presentation.ui.components.trainingcard.Grid
import com.example.myworkout.presentation.ui.components.trainingcard.GridProps
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModelFake
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun NewMuscleGroupAndSubgroup(
    viewModel: MuscleGroupViewModel,
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleGroupsWithRelation: List<MuscleGroupModel>,
    objSelected: Pair<Int, Boolean>,
    onNavigateToNewTraining: () -> Unit,
) {
    val isCardSectionVisible = muscleGroupsWithRelation.isNotEmpty()
    var showDialog by remember { mutableStateOf(false) }
    var currentAction: Action? by remember { mutableStateOf(null) }
    val utils = Utils()

    if (showDialog) {
        ActionDialog(
            action = currentAction,
            onDismiss = { showDialog = false }
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 70.dp)
            .fillMaxSize()
            .background(colorResource(R.color.global_background_color))
    ) {
        item {
            MuscleGroupSection { viewModel.insertMuscleGroup(it, BodyPart.OTHER) }
        }
        item {
            MuscleSubGroupSection(
                muscleGroups = muscleGroups,
                muscleSubGroups = muscleSubGroups,
                objSelected = objSelected,
                onItemClick = { viewModel.setMuscleGroupSelected(it) },
                utils = utils,
                isCardSectionVisible = isCardSectionVisible,
                onConfirm = {
                    viewModel.updateGroup(it)
                    showDialog = false
                },
                onDeleteGroup = {
                    viewModel.deleteGroup(it)
                    showDialog = false
                },
                onShowDialog = { value, action ->
                    showDialog = value
                    currentAction = action
                },
                onAddMuscleSubGroup = {
                    viewModel.updateSubGroup(it.copy(selected = !it.selected))
                },
                onSaveRelation = {
                    createRelations(
                        muscleSubGroups = muscleSubGroups,
                        muscleGroupId = it,
                        groups = muscleGroups,
                        onSaveRelation = { subGroups, group ->
                            viewModel.insertMuscleGroupMuscleSubGroup(subGroups)
                        }
                    )
                },
                showDialog = showDialog
            )
        }
        item {
            CardSection(
                groupsWithRelation = muscleGroupsWithRelation,
                isCardSectionVisible = isCardSectionVisible,
                onFabClick = { onNavigateToNewTraining() },
                onGroupWithRelationClicked = { onNavigateToNewTraining() }
            )
        }
    }
}

private fun createRelations(
    groups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleGroupId: Int,
    onSaveRelation: (MutableList<MuscleGroupMuscleSubGroupModel>, MuscleGroupModel?) -> Unit
) {
    val muscleGroupSubGroups: MutableList<MuscleGroupMuscleSubGroupModel> = mutableListOf()
    val subGroupsSelected: List<MuscleSubGroupModel> = muscleSubGroups.filter { it.selected }
    val group: MuscleGroupModel? = groups.find { it.muscleGroupId == muscleGroupId }

    subGroupsSelected.forEach { subGroup ->
        muscleGroupSubGroups.add(
            MuscleGroupMuscleSubGroupModel(
                muscleGroupId = muscleGroupId,
                muscleSubGroupId = subGroup.id
            )
        )
    }
    onSaveRelation(muscleGroupSubGroups, group)
}

@Composable
private fun MuscleGroupSection(onAddButtonClicked: (name: String) -> Unit) {
    var muscleGroupName by remember { mutableStateOf(String()) }
    var buttonEnabled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ButtonSection(
        modifier = Modifier,
        titleSection = stringResource(R.string.new_training),
        buttonName = stringResource(R.string.button_section_add_button),
        buttonEnabled = buttonEnabled,
        onButtonClick = {
            buttonEnabled = false
            onAddButtonClicked(muscleGroupName)
            muscleGroupName = Constants().emptyString()
            focusManager.clearFocus()
        },
        content = {
            TextFieldComponent(
                text = muscleGroupName,
                isSingleLine = true,
                onValueChange = {
                    muscleGroupName = it
                    buttonEnabled = it.isNotEmpty()
                },
                label = {
                    Text(
                        text = stringResource(R.string.new_training_input_text_label),
                        color = colorResource(R.color.title_color)
                    )
                },
                focusRequester = focusRequester
            )
        }
    )
}

@Composable
private fun MuscleSubGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    objSelected: Pair<Int, Boolean>,
    isCardSectionVisible: Boolean,
    utils: Utils,
    onConfirm: (group: MuscleGroupModel) -> Unit,
    onDeleteGroup: (group: MuscleGroupModel) -> Unit,
    onShowDialog: (value: Boolean, action: Action) -> Unit,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit,
    onSaveRelation: (muscleGroupId: Int) -> Unit,
    showDialog: Boolean
) {
    var buttonEnabled by remember { mutableStateOf(false) }

    if (muscleGroups.isNotEmpty()) {
        val muscleGroupId = objSelected.first
        val selected = objSelected.second

        ButtonSection(
            modifier = utils.setModifier(isCardSectionVisible),
            titleSection = stringResource(R.string.training),
            buttonName = stringResource(R.string.button_section_save_button),
            buttonEnabled = buttonEnabled,
            onButtonClick = { onSaveRelation(muscleGroupId) },
            content = {
                val objSelected = Pair(muscleGroupId, selected)
                Column {
                    val isMuscleGroupSelected =
                        (objSelected.second) || (muscleGroups.any { it.selected })
                    val shouldEnableSaveButton = utils.verifyEnabledButton(muscleSubGroups)
                    buttonEnabled = shouldEnableSaveButton && isMuscleGroupSelected

                    MuscleGroupSection(
                        muscleGroups = muscleGroups,
                        utils = utils,
                        objSelected = Pair(muscleGroupId, selected),
                        onConfirm = { onConfirm(it) },
                        onDeleteGroup = { onDeleteGroup(it) },
                        onShowDialog = { value, action -> onShowDialog(value, action) },
                        onItemClick = { onItemClick(Pair(it.muscleGroupId, true)) },
                        showDialog = showDialog
                    )

                    MuscleSubGroupSection(
                        muscleSubGroups = muscleSubGroups,
                        onAddMuscleSubGroup = { onAddMuscleSubGroup(it) },
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun MuscleGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    objSelected: Pair<Int, Boolean>,
    utils: Utils,
    onConfirm: (group: MuscleGroupModel) -> Unit,
    onDeleteGroup: (group: MuscleGroupModel) -> Unit,
    onShowDialog: (value: Boolean, action: Action) -> Unit,
    onItemClick: (item: MuscleGroupModel) -> Unit,
    showDialog: Boolean
) {
    val focusRequester = remember { FocusRequester() }

    Label(
        modifier = Modifier.padding(bottom = 4.dp),
        text = stringResource(R.string.select_your_training),
        fontSize = 14.sp,
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
        items(muscleGroups) { muscleGroup ->
            val selected = utils.setSelectedItem(objSelected, muscleGroup)
            val interactionSource = remember { MutableInteractionSource() }

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
                                    MuscleGroupModel(
                                        muscleGroupId = muscleGroup.muscleGroupId,
                                        name = name,
                                        image = muscleGroup.image,
                                        selected = muscleGroup.selected,
                                        enabled = muscleGroup.enabled
                                    )
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
                            })
                    )
                },
                onDoubledClick = {
                    onShowDialog(
                        true, Action.Delete(
                            title = R.string.delete_group,
                            onConfirm = { onDeleteGroup(muscleGroup) },
                            content = { Text(text = "Deseja deletar esse grupo?") }
                        )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MuscleSubGroupSection(
    muscleSubGroups: List<MuscleSubGroupModel>,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit,
) {
    Label(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(R.string.match_subgroup_with_group),
        fontSize = 14.sp,
    )

    FilterChipList(
        backGroundColor = R.color.white,
        orientation = Grid,
        orientationProps = GridProps(
            colors = Utils().selectableChipColors(),
            listOfMuscleSubGroup = muscleSubGroups,
            horizontalSpacedBy = 8.dp,
            verticalSpacedBy = 1.dp,
            onItemClick = { onAddMuscleSubGroup(it) }
        ),
    )
}

@Composable
private fun CardSection(
    groupsWithRelation: List<MuscleGroupModel>,
    isCardSectionVisible: Boolean = false,
    onFabClick: () -> Unit,
    onGroupWithRelationClicked: (muscleGroup: MuscleGroupModel) -> Unit,
) {
    if (isCardSectionVisible) {
        ButtonSection(
            modifier = Modifier.padding(bottom = 78.dp),
            titleSection = stringResource(R.string.create_training),
            buttonVisibility = false,
            content = {
                Column(verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
                    groupsWithRelation.forEach { item ->
                        ItemCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            colors = Utils().getCardColors(),
                            onClick = { onGroupWithRelationClicked(item) }
                        ) {
                            Label(
                                modifier = Modifier.padding(start = 16.dp),
                                text = item.name,
                                fontSize = 14.sp,
                                textColor = colorResource(R.color.text_color)
                            )
                        }
                    }
                    FabSection(onClick = { onFabClick() })
                }
            }
        )
    }
}

@Composable
@Preview
private fun NewMuscleGroupAndSubgroupPreview() {
    NewMuscleGroupAndSubgroup(
        viewModel = MuscleGroupViewModelFake(),
        muscleGroups = Constants().groupsMock,
        muscleGroupsWithRelation = listOf(),
        objSelected = Pair(0, false),
        muscleSubGroups = Constants().getAllSubGroupsMock(),
        onNavigateToNewTraining = {}
    )
}
