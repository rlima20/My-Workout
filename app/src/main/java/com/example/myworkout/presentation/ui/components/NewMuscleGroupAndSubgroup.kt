package com.example.myworkout.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.Orientation
import com.example.myworkout.extensions.emptyString
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.commons.Label
import com.example.myworkout.presentation.ui.components.musclegroup.ItemCard
import com.example.myworkout.presentation.ui.components.trainingcard.DEFAULT_PADDING
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.utils.Utils
import com.example.myworkout.utils.getCardColors

@SuppressLint("MutableCollectionMutableState")
@Composable
fun NewMuscleGroupAndSubgroup(
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleGroupsWithRelation: List<MuscleGroupModel>,
    onCreateMuscleGroup: (name: String) -> Unit,
    objSelected: Pair<Int, Boolean>,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onGroupWithRelationClicked: (muscleGroup: MuscleGroupModel) -> Unit,
    onUpdateSubGroup: (subGroup: MuscleSubGroupModel) -> Unit,
    onSaveRelation: (MutableList<MuscleGroupMuscleSubGroupModel>) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(top = 70.dp).fillMaxSize()) {
        item {
            SetMuscleGroupSection { onCreateMuscleGroup(it) }
        }
        item {
            SetMuscleSubGroupSection(
                muscleGroups = muscleGroups,
                muscleSubGroups = muscleSubGroups,
                objSelected = objSelected,
                onItemClick = { onItemClick(it) },
                onAddMuscleSubGroup = { verifySubGroupSelected(it, onUpdateSubGroup) },
                onSaveRelation = { createRelations(muscleSubGroups, it, onSaveRelation) }
            )
        }
        item {
            SetCardSection(
                muscleGroupsWithRelation = muscleGroupsWithRelation,
                onGroupWithRelationClicked = { onGroupWithRelationClicked(it) })
        }
    }
}

private fun createRelations(
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleGroupId: Int,
    onSaveRelation: (MutableList<MuscleGroupMuscleSubGroupModel>) -> Unit
) {
    val muscleGroupSubGroups: MutableList<MuscleGroupMuscleSubGroupModel> = mutableListOf()

    val subGroupsSelected: List<MuscleSubGroupModel> = muscleSubGroups.filter { it.selected }

    subGroupsSelected.forEach { subGroup ->
        muscleGroupSubGroups.add(
            MuscleGroupMuscleSubGroupModel(
                muscleGroupId = muscleGroupId,
                muscleSubGroupId = subGroup.id
            )
        )
    }
    onSaveRelation(muscleGroupSubGroups)
}

private fun verifySubGroupSelected(
    subGroupSelected: MuscleSubGroupModel,
    onUpdateSubGroup: (MuscleSubGroupModel) -> Unit
) {
    onUpdateSubGroup(subGroupSelected.copy(selected = !subGroupSelected.selected))
}

@Composable
private fun SetMuscleGroupSection(onAddButtonClicked: (name: String) -> Unit) {
    var muscleGroupName by remember { mutableStateOf(String()) }
    var buttonEnabled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ButtonSection(
        modifier = Modifier,
        titleSection = stringResource(R.string.new_muscle_group),
        buttonName = stringResource(R.string.button_section_add_button),
        buttonEnabled = buttonEnabled,
        onButtonClick = {
            buttonEnabled = false
            onAddButtonClicked(muscleGroupName)
            muscleGroupName = String().emptyString()
            focusManager.clearFocus()
        },
        content = {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = muscleGroupName,
                singleLine = true,
                onValueChange = {
                    muscleGroupName = it
                    buttonEnabled = it.isNotEmpty()
                },
                label = { Text(stringResource(R.string.new_training_input_text_label)) }
            )
        }
    )
}

@Composable
private fun SetMuscleSubGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    objSelected: Pair<Int, Boolean>,
    onItemClick: (Pair<Int, Boolean>) -> Unit,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit,
    onSaveRelation: (muscleGroupId: Int) -> Unit,
) {
    var buttonEnabled by remember { mutableStateOf(false) }

    if (muscleGroups.isNotEmpty()) {
        val muscleGroupId = objSelected.first
        val selected = objSelected.second

        ButtonSection(
            modifier = Modifier,
            titleSection = stringResource(R.string.new_sub_group),
            buttonName = stringResource(R.string.button_section_save_button),
            buttonEnabled = buttonEnabled,
            onButtonClick = { onSaveRelation(muscleGroupId) },
            content = {
                val objSelected = Pair(muscleGroupId, selected)
                Column {
                    val isMuscleGroupSelected =
                        (objSelected.second) || (muscleGroups.any { it.selected })
                    val shouldEnableSaveButton = verifyEnabledButton(muscleSubGroups)
                    buttonEnabled = shouldEnableSaveButton && isMuscleGroupSelected

                    MuscleGroupSection(
                        muscleGroups = muscleGroups,
                        objSelected = Pair(muscleGroupId, selected),
                        onItemClick = { onItemClick(Pair(it.muscleGroupId, true)) }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MuscleGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    objSelected: Pair<Int, Boolean>,
    onItemClick: (item: MuscleGroupModel) -> Unit,
) {
    Label(
        modifier = Modifier.padding(bottom = 4.dp),
        text = stringResource(R.string.select_your_group),
        fontSize = 14.sp,
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
        items(muscleGroups) { muscleGroup ->
            FilterChip(
                border = BorderStroke(0.5.dp, color = colorResource(R.color.pending)),
                enabled = muscleGroup.enabled,
                modifier = Modifier.height(42.dp),
                colors = Utils().selectableChipColors(),
                selected = setSelectedItem(objSelected, muscleGroup),
                content = { MuscleGroupName(muscleGroup) },
                onClick = { onItemClick(muscleGroup) },
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
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        text = stringResource(R.string.match_subgroup_with_group),
        fontSize = 14.sp,
    )
    FilterChipList(
        modifier = Modifier.height(28.dp),
        colors = Utils().selectableChipColors(),
        muscleSubGroups = muscleSubGroups,
        orientation = Orientation.VERTICAL_GRID,
        onItemClick = { onAddMuscleSubGroup(it) }
    )
}

@Composable
private fun SetCardSection(
    muscleGroupsWithRelation: List<MuscleGroupModel>,
    onGroupWithRelationClicked: (muscleGroup: MuscleGroupModel) -> Unit
) {
    if (muscleGroupsWithRelation.isNotEmpty()) {
        ButtonSection(
            modifier = Modifier.padding(bottom = 68.dp),
            titleSection = stringResource(R.string.create_training),
            buttonVisibility = false,
            content = {
                Column(verticalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
                    muscleGroupsWithRelation.forEach { item ->
                        ItemCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            colors = getCardColors(),
                            onClick = { onGroupWithRelationClicked(item) }
                        ) {
                            Label(
                                modifier = Modifier.padding(start = 16.dp),
                                text = item.name,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }
        )
    }
}

private fun verifyEnabledButton(muscleSubGroupsSelected: List<MuscleSubGroupModel>): Boolean {
    return muscleSubGroupsSelected.any { it.selected }
}

@Composable
private fun MuscleGroupName(muscleGroup: MuscleGroupModel) {
    Text(
        color = colorResource(R.color.white),
        fontSize = 18.sp,
        text = muscleGroup.name,
        maxLines = 1,
        overflow = TextOverflow.Visible, // ou Clip, se não quiser "..."
        softWrap = false
    )
}

@Composable
private fun setSelectedItem(objSelected: Pair<Int, Boolean>, muscleGroup: MuscleGroupModel) =
    if (objSelected.first == muscleGroup.muscleGroupId) objSelected.second else muscleGroup.selected

@Composable
@Preview
private fun NewMuscleGroupAndSubgroupPreview() {
    NewMuscleGroupAndSubgroup(
        muscleGroups = Constants().muscleGroups,
        muscleGroupsWithRelation = listOf(),
        onCreateMuscleGroup = {},
        objSelected = Pair(0, false),
        onItemClick = {},
        onGroupWithRelationClicked = {},
        muscleSubGroups = Constants().chestAndTricepsSubGroups,
        onUpdateSubGroup = {},
        onSaveRelation = {},
    )
}
