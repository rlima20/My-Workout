package com.example.myworkout.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.domain.mapper.toListOfMuscleGroup
import com.example.myworkout.domain.model.MuscleGroup
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.trainingcard.DEFAULT_PADDING

@Composable
fun NewMuscleGroupAndSubgroup(
    muscleGroups: List<MuscleGroupModel>,
    onCreateMuscleGroup: (name: String) -> Unit,
    enableSubGroupSection: Boolean
) {
    Column(modifier = Modifier.padding(top = 70.dp)) {
        SetMuscleGroupSection { onCreateMuscleGroup(it) }
        SetMuscleSubGroupSection(
            muscleGroups = muscleGroups,
            enableSubGroupSection = enableSubGroupSection
        )
    }
}

@Composable
fun SetMuscleGroupSection(onAddButtonClicked: (name: String) -> Unit) {

    var muscleGroupName by remember { mutableStateOf(String()) }
    var buttonEnabled by remember { mutableStateOf(false) }
    var enableTextField by remember { mutableStateOf(true) }

    ButtonSection(
        modifier = Modifier,
        buttonName = stringResource(R.string.button_section_add_button),
        buttonEnabled = buttonEnabled,
        onButtonClick = {
            buttonEnabled = false
            enableTextField = false
            onAddButtonClicked(muscleGroupName)
        },
        content = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = muscleGroupName,
                onValueChange = {
                    muscleGroupName = it
                    buttonEnabled = it.isNotEmpty()
                },
                enabled = enableTextField,
                label = { Text(stringResource(R.string.new_training_input_text_label)) }
            )
        }
    )
}

@Composable
fun SetMuscleSubGroupSection(
    muscleGroups: List<MuscleGroupModel>,
    enableSubGroupSection: Boolean
) {
    var muscleGroupList by remember { mutableStateOf(muscleGroups.toListOfMuscleGroup()) }

    ButtonSection(
        modifier = Modifier,
        buttonName = stringResource(R.string.button_section_save_button),
        buttonEnabled = enableSubGroupSection,
        onButtonClick = {
            // Todo
        },
        content = {
            MuscleGroups(
                muscleGroups = muscleGroupList,
                onItemClick = {
                    muscleGroupList = muscleGroupList.map { muscleGroup ->
                        MuscleGroup(
                            muscleGroupId = muscleGroup.muscleGroupId,
                            name = muscleGroup.name,
                            image = muscleGroup.image,
                            selected = muscleGroup.muscleGroupId == it.muscleGroupId,
                            enabled = muscleGroup.muscleGroupId == it.muscleGroupId
                        )
                    }
                })
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MuscleGroups(
    muscleGroups: List<MuscleGroup>,
    onItemClick: (item: MuscleGroup) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(DEFAULT_PADDING)) {
        items(muscleGroups) { item ->

            FilterChip(
                border = BorderStroke(1.dp, Color(0x7FE1E1E1)),
                enabled = item.enabled,
                shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 15)),
                modifier = Modifier.height(42.dp),
                colors = ChipDefaults.filterChipColors(
                    backgroundColor = Color(0xB2DCDCDC),
                    selectedContentColor = Color(0xFF070707),
                    disabledBackgroundColor = Color(0xFFCDC7D1),
                    disabledContentColor = Color(0x7FFFFFFF),
                    selectedBackgroundColor = Color(0xFF9C93A6)
                ),
                onClick = { onItemClick(item) },
                selected = item.selected,
                content = { Text(fontSize = 18.sp, text = item.name) },
            )
        }
    }
}

@Composable
@Preview
fun NewMuscleGroupAndSubgroupPreview() {
    NewMuscleGroupAndSubgroup(
        muscleGroups = mutableListOf(
            MuscleGroupModel(
                muscleGroupId = 1,
                name = "Peito e Ombro",
                image = BodyPart.OTHER
            ),
            MuscleGroupModel(
                muscleGroupId = 2,
                name = "Ombro",
                image = BodyPart.OTHER
            ),
            MuscleGroupModel(
                muscleGroupId = 3,
                name = "Braço",
                image = BodyPart.OTHER
            ),
            MuscleGroupModel(
                muscleGroupId = 4,
                name = "Pernas",
                image = BodyPart.OTHER
            ),
            MuscleGroupModel(
                muscleGroupId = 5,
                name = "Abdômen",
                image = BodyPart.OTHER
            ),
        ),
        onCreateMuscleGroup = {},
        enableSubGroupSection = true
    )
}


