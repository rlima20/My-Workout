package com.example.myworkout.presentation.ui.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.myworkout.enums.Sort
import com.example.myworkout.presentation.ui.components.commons.Action
import com.example.myworkout.presentation.ui.components.commons.ActionDialog
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.commons.CustomSearchBar
import com.example.myworkout.presentation.ui.components.commons.Divider
import com.example.myworkout.presentation.ui.components.commons.Label
import com.example.myworkout.presentation.ui.components.commons.TextFieldComponent
import com.example.myworkout.presentation.ui.components.commons.TextIcon
import com.example.myworkout.presentation.ui.components.commons.TwoOptionToggle
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.presentation.ui.components.trainingcard.Grid
import com.example.myworkout.presentation.ui.components.trainingcard.GridMuscleGroup
import com.example.myworkout.presentation.ui.components.trainingcard.GridMuscleGroupProps
import com.example.myworkout.presentation.ui.components.trainingcard.GridProps
import com.example.myworkout.presentation.ui.components.trainingcard.GridTraining
import com.example.myworkout.presentation.ui.components.trainingcard.GridTrainingProps
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModelFake
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MuscleConfig(
    query: String,
    noResult: Boolean,
    viewModel: MuscleGroupViewModel,
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleGroupsWithRelation: List<MuscleGroupModel>,
    selectedSort: String,
    objSelected: Pair<Int, Boolean>,
    onNavigateToNewTraining: () -> Unit,
) {
    var innerMuscleSubGroups: List<MuscleSubGroupModel> = muscleSubGroups
    var innerQuery = query

    LaunchedEffect(viewModel.query) { innerMuscleSubGroups = muscleSubGroups }
    LaunchedEffect(viewModel.query) { innerQuery = query }

    var showDialog by remember { mutableStateOf(false) }
    var currentAction: Action? by remember { mutableStateOf(null) }
    val utils = Utils()
    var showSubGroupsSelectionSection by remember { mutableStateOf(false) }

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
            GroupSelectionSection(
                muscleGroups = muscleGroups,
                objSelected = objSelected,
                onItemClick = { viewModel.setMuscleGroupSelected(it) },
                utils = utils,
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
                onSaveRelation = {
                    createRelations(
                        muscleSubGroups = innerMuscleSubGroups,
                        muscleGroupId = it,
                        groups = muscleGroups,
                        onSaveRelation = { relation, group ->
                            viewModel.insertMuscleGroupMuscleSubGroup(
                                muscleGroupMuscleSubGroups = relation
                            )
                        },
                    )
                },
                showDialog = showDialog,
                onShowSubGroupsSelectionSection = { showSubGroupsSelectionSection = it }
            )
        }
        item {
            SubGroupsSelectionSection(
                showSubGroupsSelectionSection = showSubGroupsSelectionSection,
                muscleGroups = muscleGroups,
                muscleSubGroups = innerMuscleSubGroups,
                objSelected = objSelected,
                utils = utils,
                onShowDialog = { value, action ->
                    showDialog = value
                    currentAction = action
                },
                onAddMuscleSubGroup = {
                    viewModel.updateSubGroup(it.copy(selected = !it.selected))
                },
                onSaveRelation = {
                    createRelations(
                        muscleSubGroups = innerMuscleSubGroups,
                        muscleGroupId = it,
                        groups = muscleGroups,
                        onSaveRelation = { relation, group ->
                            viewModel.insertMuscleGroupMuscleSubGroup(
                                muscleGroupMuscleSubGroups = relation
                            )
                        },
                    )
                    viewModel.clearQuery()
                    viewModel.sortAndFilterSubGroups()
                },
                onCreateNewSubgroup = {
                    viewModel.insertNewSubGroup(it)
                    showDialog = false
                },
                onSelectSort = { viewModel.setSelectedSort(it) },
                selectedSort = selectedSort,
                onSearch = {
                    viewModel.setQuery(it)
                    viewModel.sortAndFilterSubGroups()
                },
                onClear = {
                    viewModel.clearQuery()
                    viewModel.sortAndFilterSubGroups()
                },
                query = innerQuery,
                noResult = noResult
            )
        }
        item {
            CardSection(
                groupsWithRelation = muscleGroupsWithRelation,
                enabled = muscleGroupsWithRelation.isNotEmpty(),
                onButtonClick = { onNavigateToNewTraining() },
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
    val muscleSubGroupsSelected: List<MuscleSubGroupModel> = muscleSubGroups.filter { it.selected }
    val group: MuscleGroupModel? = groups.find { it.muscleGroupId == muscleGroupId }

    muscleSubGroupsSelected.forEach { subGroup ->
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
        titleSection = stringResource(R.string.create_muscle_group),
        firstButtonName = stringResource(R.string.button_section_add_group),
        firstButtonEnabled = buttonEnabled,
        onFirstButtonClick = {
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
private fun GroupSelectionSection(
    muscleGroups: List<MuscleGroupModel>,
    objSelected: Pair<Int, Boolean>,
    utils: Utils,
    onConfirm: (group: MuscleGroupModel) -> Unit,
    onDeleteGroup: (group: MuscleGroupModel) -> Unit,
    onShowDialog: (value: Boolean, action: Action) -> Unit,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onSaveRelation: (muscleGroupId: Int) -> Unit,
    showDialog: Boolean,
    onShowSubGroupsSelectionSection: (value: Boolean) -> Unit
) {
    Divider()
    val muscleGroupId = objSelected.first
    val selected = objSelected.second

    ButtonSection(
        cardColors = if (muscleGroups.isNotEmpty()) utils.buttonSectionCardsColors() else utils.buttonSectionCardsDisabledColors(),
        titleSection = stringResource(R.string.groups),
        buttonVisibility = false,
        onFirstButtonClick = { onSaveRelation(muscleGroupId) },
        onSecondButtonClick = {},
        content = {
            val objSelected = Pair(muscleGroupId, selected)
            Column {
                val isMuscleGroupSelected =
                    (objSelected.second) || (muscleGroups.any { it.selected })

                onShowSubGroupsSelectionSection(isMuscleGroupSelected)

                MuscleGroupSection(
                    muscleGroups = muscleGroups,
                    objSelected = Pair(muscleGroupId, selected),
                    onConfirm = { onConfirm(it) },
                    onDeleteGroup = { onDeleteGroup(it) },
                    onShowDialog = { value, action -> onShowDialog(value, action) },
                    onItemClick = { onItemClick(Pair(it.muscleGroupId, true)) },
                    showDialog = showDialog
                )
            }
        }
    )
}

@Composable
private fun SubGroupsSelectionSection(
    showSubGroupsSelectionSection: Boolean,
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    objSelected: Pair<Int, Boolean>,
    utils: Utils,
    selectedSort: String,
    query: String,
    noResult: Boolean,
    onSearch: (text: String) -> Unit,
    onClear: () -> Unit,
    onShowDialog: (value: Boolean, action: Action) -> Unit,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit,
    onSaveRelation: (muscleGroupId: Int) -> Unit,
    onCreateNewSubgroup: (subGroupName: String) -> Unit,
    onSelectSort: (selectedSort: String) -> Unit,
) {
    val constants = Constants()
    var newSubgroup by remember { mutableStateOf(constants.emptyString()) }
    val focusRequester = remember { FocusRequester() }
    var buttonEnabled by remember { mutableStateOf(false) }

    Divider()
    val muscleGroupId = objSelected.first

    val isMuscleGroupSelected = (objSelected.second) || (muscleGroups.any { it.selected })
    val shouldEnableSaveButton = utils.verifyEnabledButton(muscleSubGroups)
    buttonEnabled = shouldEnableSaveButton && isMuscleGroupSelected

    ButtonSection(
        isDualButton = true,
        cardColors = if (showSubGroupsSelectionSection) utils.buttonSectionCardsColors() else utils.buttonSectionCardsDisabledColors(),
        titleSection = stringResource(R.string.join_subgroups),
        firstButtonName = stringResource(R.string.button_section_save_button),
        secondButtonName = stringResource(R.string.new_subgroup),
        firstButtonEnabled = buttonEnabled,
        firstButtonHintEnabled = ((!isMuscleGroupSelected) || (!buttonEnabled)),
        firstButtonHintText = setFirstButtonHintText(isMuscleGroupSelected, buttonEnabled),
        secondButtonEnabled = showSubGroupsSelectionSection,
        onFirstButtonClick = { onSaveRelation(muscleGroupId) },
        onSecondButtonClick = {
            onShowDialog(
                true,
                Action.Edit(
                    title = R.string.create_new_subgroup,
                    onConfirm = {
                        onCreateNewSubgroup(newSubgroup)
                        newSubgroup = constants.emptyString()
                    },
                    content = {
                        TextFieldComponent(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = newSubgroup,
                            isSingleLine = true,
                            focusRequester = focusRequester,
                            onValueChange = { newSubgroup = it },
                            enabled = true,
                            label = {
                                Text(
                                    text = stringResource(R.string.subgroup_name_string),
                                    color = colorResource(R.color.title_color),
                                    fontSize = 16.sp
                                )
                            }
                        )
                    },
                )
            )
        },
        content = {
            Label(
                modifier = Modifier.padding(top = 6.dp),
                text = stringResource(R.string.join_groups_description2),
                style = TextStyle(lineHeight = 14.sp),
                fontSize = 14.sp,
            )

            if (showSubGroupsSelectionSection) {
                MuscleSubGroupSection(
                    query = query,
                    selectedSort = selectedSort,
                    muscleSubGroups = muscleSubGroups,
                    noResult = noResult,
                    onSearch = { onSearch(it) },
                    onClear = { onClear() },
                    onSelectSort = { onSelectSort(it) },
                    onAddMuscleSubGroup = { onAddMuscleSubGroup(it) }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MuscleGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    objSelected: Pair<Int, Boolean>,
    onConfirm: (group: MuscleGroupModel) -> Unit,
    onDeleteGroup: (group: MuscleGroupModel) -> Unit,
    onShowDialog: (value: Boolean, action: Action) -> Unit,
    onItemClick: (item: MuscleGroupModel) -> Unit,
    showDialog: Boolean
) {
    if (muscleGroups.isNotEmpty()) {
        Label(
            modifier = Modifier.padding(bottom = 6.dp),
            text = stringResource(R.string.join_groups),
            style = TextStyle(lineHeight = 14.sp),
            fontSize = 14.sp,
        )
    } else {
        Text(
            text = stringResource(R.string.join_groups_description),
            color = colorResource(R.color.missed),
            fontSize = 12.sp,
            maxLines = 1,
        )
    }

    FilterChipList(
        modifier = Modifier.fillMaxWidth(),
        backGroundColor = R.color.white,
        orientation = GridMuscleGroup,
        orientationProps = GridMuscleGroupProps(
            listOfMuscleGroup = muscleGroups,
            objSelected = objSelected,
            showDialog = showDialog,
            onItemClick = { onItemClick(it) },
            onConfirm = { onConfirm(it) },
            onDeleteGroup = { onDeleteGroup(it) },
            onShowDialog = { value, action -> onShowDialog(value, action) },
        )
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MuscleSubGroupSection(
    selectedSort: String,
    noResult: Boolean,
    muscleSubGroups: List<MuscleSubGroupModel>,
    query: String,
    onSearch: (text: String) -> Unit,
    onClear: () -> Unit,
    onSelectSort: (selectedSort: String) -> Unit,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit,
) {
    var selectedSortInner by remember { mutableStateOf(selectedSort) }
    LaunchedEffect(selectedSort) { selectedSortInner = selectedSort }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextIcon(
                modifier = Modifier.padding(end = 8.dp),
                text = stringResource(R.string.sort),
                icon = painterResource(R.drawable.sort)
            )
            TwoOptionToggle(
                selectedSort = selectedSortInner,
                optionA = Sort().sortAZ,
                optionB = Sort().sortZA,
                onSelected = { onSelectSort(it) }
            )
        }
    }

    CustomSearchBar(
        modifier = Modifier.padding(bottom = 8.dp),
        query = query,
        onValueChange = { onSearch(it) },
        onClear = { onClear() }
    )

    if (noResult) {
        TextIcon(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 18.sp,
            iconSize = 20.dp,
            spaceBetweenIconAndText = 4.dp,
            textColor = colorResource(R.color.missed),
            text = stringResource(R.string.no_subgroup_found),
            icon = painterResource(R.drawable.baseline_warning_24),
            tintColor = colorResource(R.color.missed)
        )
    }

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CardSection(
    enabled: Boolean = false,
    groupsWithRelation: List<MuscleGroupModel>,
    onGroupWithRelationClicked: (muscleGroup: MuscleGroupModel) -> Unit,
    onButtonClick: () -> Unit
) {
    val utils = Utils()
    Divider()
    ButtonSection(
        firstButtonEnabled = enabled,
        cardColors = if (enabled) utils.buttonSectionCardsColors() else utils.buttonSectionCardsDisabledColors(),
        modifier = Modifier.padding(bottom = 78.dp),
        titleSection = stringResource(R.string.create_training),
        buttonVisibility = true,
        firstButtonHintEnabled = !enabled,
        firstButtonHintText = stringResource(R.string.join_group_to_subgroup),
        firstButtonName = stringResource(R.string.new_training),
        onFirstButtonClick = { onButtonClick() },
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
                Label(
                    text = stringResource(R.string.configured_group),
                    fontSize = 14.sp,
                    modifier = Modifier
                )

                FilterChipList(
                    modifier = Modifier,
                    backGroundColor = R.color.white,
                    orientation = GridTraining,
                    orientationProps = GridTrainingProps(
                        colors = Utils().selectableChipColors(),
                        listOfMuscleGroup = groupsWithRelation,
                        horizontalSpacedBy = 8.dp,
                        verticalSpacedBy = 1.dp,
                        onItemClick = { onGroupWithRelationClicked(it) }
                    )
                )
            }
        }
    )
}

@Composable
private fun setFirstButtonHintText(
    isMuscleGroupSelected: Boolean,
    buttonEnabled: Boolean
): String =
    if (!isMuscleGroupSelected && !buttonEnabled)
        stringResource(R.string.join_select_subgroups)
    else if (isMuscleGroupSelected && !buttonEnabled)
        stringResource(R.string.join_groups_description)
    else stringResource(R.string.join_select_subgroups)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun NewMuscleGroupAndSubgroupPreview() {
    MuscleConfig(
        viewModel = MuscleGroupViewModelFake(),
        muscleGroups = Constants().groupsMock,
        muscleGroupsWithRelation = listOf(),
        objSelected = Pair(0, false),
        muscleSubGroups = Constants().getAllSubGroupsFewMock(),
        onNavigateToNewTraining = {},
        selectedSort = Sort().sortAZ,
        query = "",
        noResult = false
    )
}
