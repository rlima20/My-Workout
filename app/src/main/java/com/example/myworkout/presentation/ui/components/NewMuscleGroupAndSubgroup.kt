package com.example.myworkout.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.Orientation
import com.example.myworkout.extensions.emptyString
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.trainingcard.DEFAULT_PADDING
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.utils.selectableChipColors


@SuppressLint("MutableCollectionMutableState")
@Composable
fun NewMuscleGroupAndSubgroup(
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    enableSubGroupSection: Boolean,
    onCreateMuscleGroup: (name: String) -> Unit,
) {
    var muscleSubGroupsSelected: MutableList<MuscleSubGroupModel> by remember {
        mutableStateOf(
            mutableListOf()
        )
    }
    var newMuscleSubGroupsSelected: MutableList<MuscleSubGroupModel> by remember {
        mutableStateOf(
            mutableListOf()
        )
    }

    Column(modifier = Modifier.padding(top = 70.dp)) {
        SetMuscleGroupSection { onCreateMuscleGroup(it) }
        SetMuscleSubGroupSection(
            muscleGroups = muscleGroups,
            muscleSubGroups = muscleSubGroups,
            muscleSubGroupsSelected = newMuscleSubGroupsSelected,
            enableSubGroupSection = enableSubGroupSection,
            onAddMuscleSubGroup = {
                if (!it.selected) {
                    muscleSubGroupsSelected.add(it)
                    val updatedMuscleSubGroups = muscleSubGroups.map { subGroup ->
                        if (muscleSubGroupsSelected.contains(subGroup)) subGroup.copy(selected = true)
                        else subGroup.copy(selected = false)
                    }
                    newMuscleSubGroupsSelected = updatedMuscleSubGroups.toMutableList()
                } else {
                    val updatedMuscleSubGroups = muscleSubGroups.map { subGroup ->
                        if (muscleSubGroupsSelected.contains(subGroup)) subGroup.copy(selected = false)
                        else subGroup.copy(selected = true)
                    }
                    newMuscleSubGroupsSelected = updatedMuscleSubGroups.toMutableList()
                }
            }
        )
    }
}

@Composable
fun SetMuscleGroupSection(onAddButtonClicked: (name: String) -> Unit) {

    var muscleGroupName by remember { mutableStateOf(String()) }
    var buttonEnabled by remember { mutableStateOf(false) }
    var textFieldFocused by remember { mutableStateOf(true) }
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
            textFieldFocused = false
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
fun SetMuscleSubGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleSubGroupsSelected: List<MuscleSubGroupModel>,
    enableSubGroupSection: Boolean,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit
) {
    if (muscleGroups.isNotEmpty()) {
        var selected by remember { mutableStateOf(false) }
        var muscleGroupId by remember { mutableIntStateOf(0) }

        ButtonSection(
            modifier = Modifier,
            titleSection = stringResource(R.string.new_sub_group),
            buttonName = stringResource(R.string.button_section_save_button),
            buttonEnabled = enableSubGroupSection,
            onButtonClick = {},
            content = {
                MuscleGroupsWithMuscleSubGroups(
                    objSelected = Pair(muscleGroupId, selected),
                    muscleGroups = muscleGroups,
                    muscleSubGroups = muscleSubGroups,
                    muscleSubGroupsSelected = muscleSubGroupsSelected,
                    onItemClick = {
                        selected = true
                        muscleGroupId = it.muscleGroupId
                    },
                    onAddMuscleSubGroup = { onAddMuscleSubGroup(it) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MuscleGroupsWithMuscleSubGroups(
    objSelected: Pair<Int, Boolean>,
    muscleGroups: List<MuscleGroupModel>,
    muscleSubGroups: List<MuscleSubGroupModel>,
    muscleSubGroupsSelected: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleGroupModel) -> Unit,
    onAddMuscleSubGroup: (item: MuscleSubGroupModel) -> Unit
) {
    Column {
        MuscleGroupLabel()
        LazyRow(horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
            items(muscleGroups) { muscleGroup ->
                FilterChip(
                    border = BorderStroke(0.5.dp, color = colorResource(R.color.pending)),
                    enabled = muscleGroup.enabled,
                    shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 15)),
                    modifier = Modifier.height(42.dp),
                    colors = selectableChipColors(),
                    selected = setSelectedItem(objSelected, muscleGroup),
                    content = { Text(fontSize = 18.sp, text = muscleGroup.name) },
                    onClick = { onItemClick(muscleGroup) },
                )
            }
        }

        MuscleSubGroupLabel()
        FilterChipList(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp),
            muscleSubGroups = muscleSubGroupsSelected.ifEmpty { muscleSubGroups },
            orientation = Orientation.HORIZONTAL,
            onItemClick = { onAddMuscleSubGroup(it) }
        )
    }
}

@Composable
private fun MuscleGroupLabel() {
    Text(
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 4.dp),
        text = stringResource(R.string.select_your_group)
    )
}

@Composable
private fun MuscleSubGroupLabel() {
    Text(
        fontSize = 14.sp,
        modifier = Modifier.padding(top = 16.dp),
        text = "(Selecione os subgrupos que quer vincular ao grupo escolhido)"
    )
}

@Composable
private fun setSelectedItem(objSelected: Pair<Int, Boolean>, muscleGroup: MuscleGroupModel) =
    if (objSelected.first == muscleGroup.muscleGroupId) objSelected.second else muscleGroup.selected

@Composable
@Preview
fun NewMuscleGroupAndSubgroupPreview() {
    NewMuscleGroupAndSubgroup(
        muscleGroups = Constants().muscleGroups,
        onCreateMuscleGroup = {},
        enableSubGroupSection = true,
        muscleSubGroups = Constants().muscleSubGroups
    )
}


