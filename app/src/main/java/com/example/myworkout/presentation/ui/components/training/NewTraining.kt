package com.example.myworkout.presentation.ui.components.training

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.extractGroups
import com.example.myworkout.extensions.extractSubGroupsByGroup
import com.example.myworkout.extensions.toListOfDays
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.commons.DropdownItem
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.presentation.ui.components.trainingcard.Grid
import com.example.myworkout.presentation.ui.components.trainingcard.GridProps
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun NewTraining(
    groupsAndSubgroupsWithRelations: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>,
    onFetchRelations: () -> Unit,
    onSaveTraining: () -> Unit = {}
) {
    onFetchRelations()
    val utils = Utils()
    var text by remember { mutableStateOf(utils.weekToString(DayOfWeek.values().first())) }
    var firstGroup by remember { mutableStateOf(getDefaultGroup()) }

    Column(Modifier.fillMaxSize()) {
        TabRowComponent(
            muscleGroups = groupsAndSubgroupsWithRelations.extractGroups(),
            onItemSelected = {
                groupsAndSubgroupsWithRelations.extractSubGroupsByGroup(it)
                firstGroup = it
            },
        )
        ButtonSection(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 78.dp)
                .fillMaxHeight(),
            titleSection = stringResource(R.string.subgroups),
            buttonName = stringResource(R.string.button_section_save_button),
            buttonEnabled = true,
            onButtonClick = { onSaveTraining() },
            content = {
                FilterChipList(
                    modifier = Modifier.padding(bottom = 8.dp),
                    backGroundColor = R.color.white,
                    orientation = Grid,
                    orientationProps = GridProps(
                        colors = Utils().selectableChipColors(),
                        listOfMuscleSubGroup = groupsAndSubgroupsWithRelations
                            .extractSubGroupsByGroup(
                                firstGroup
                            ),
                        enabled = false,
                        onItemClick = {},
                        horizontalSpacedBy = 8.dp,
                        verticalSpacedBy = 1.dp,
                    ),
                )
                DropdownItem(
                    items = DayOfWeek.values().toListOfDays(),
                    text = text,
                    onItemClick = { text = it }
                )
            }
        )
    }
}

@Preview
@Composable
private fun NewTrainingPreview() {
    NewTraining(
        groupsAndSubgroupsWithRelations = Constants().getGroupsAndSubgroupsWithRelations(),
        onFetchRelations = {},
        onSaveTraining = {}
    )
}

private fun getDefaultGroup(): MuscleGroupModel =
    MuscleGroupModel(
        muscleGroupId = 0,
        name = "",
        BodyPart.LEG
    )