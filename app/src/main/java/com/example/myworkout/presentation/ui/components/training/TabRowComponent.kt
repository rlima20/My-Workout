package com.example.myworkout.presentation.ui.components.training

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.ui.components.home.ErrorStateComponent

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun TabRowComponent(
    muscleGroups: List<MuscleGroupModel> = emptyList(),
    onCreateImageSection: @Composable (bodyPartImage: BodyPart) -> Unit
) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val muscleGroupList = remember { mutableStateOf(muscleGroups) }
    val muscleGroupItem = remember { mutableStateOf(muscleGroups[0]) }

    Column(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            top = 80.dp
        )
    ) {
        ValidateDataForScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            muscleGroupList = muscleGroupList,
            muscleGroups = muscleGroups,
            muscleGroupItem = muscleGroupItem,
            onCreateImageSection = { onCreateImageSection(it) }
        )
    }
}

@Composable
private fun ValidateDataForScrollableTabRow(
    selectedTabIndex: MutableIntState,
    muscleGroupList: MutableState<List<MuscleGroupModel>>,
    muscleGroups: List<MuscleGroupModel>,
    muscleGroupItem: MutableState<MuscleGroupModel>,
    onCreateImageSection: @Composable (bodyPartImage: BodyPart) -> Unit
) {
    if (selectedTabIndex.value in muscleGroupList.value.indices) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            backgroundColor = Color(0x2A8D8D8D),
            edgePadding = 4.dp,
            tabs = {
                SetTabs(
                    muscleGroups = muscleGroups,
                    selectedTabIndex = selectedTabIndex,
                    muscleGroupItem = muscleGroupItem
                )
            }
        )
        CreateTabContent(
            muscleGroupItem = muscleGroupItem.value,
            onCreateImageSection = { onCreateImageSection(it) }
        )
    } else {
        ErrorStateComponent()
    }
}

@SuppressLint("AutoboxingStateValueProperty")
@Composable
private fun SetTabs(
    muscleGroups: List<MuscleGroupModel>,
    selectedTabIndex: MutableIntState,
    muscleGroupItem: MutableState<MuscleGroupModel>
) {
    muscleGroups.forEachIndexed { index, muscleGroup ->
        Tab(
            selected = selectedTabIndex.value == index,
            onClick = {
                muscleGroupItem.value = muscleGroup
                selectedTabIndex.value = index
            },
            text = { Text(muscleGroup.name) },
            modifier = Modifier.width(120.dp)
        )
    }
}

@Composable
fun CreateTabContent(
    muscleGroupItem: MuscleGroupModel,
    onCreateImageSection: @Composable (bodyPartImage: BodyPart) -> Unit
) {
    TabContent(
        muscleGroupItem = muscleGroupItem,
        onCreateImageSection = { onCreateImageSection(it) }
    )
}

@Composable
fun TabContent(
    muscleGroupItem: MuscleGroupModel,
    onCreateImageSection: @Composable (bodyPartImage: BodyPart) -> Unit,
) {
    onCreateImageSection(muscleGroupItem.image)
}

@Preview
@Composable
private fun TabRowComponentPreview(){
    TabRowComponent(
        Constants().groupsMock
    ) {  }
}