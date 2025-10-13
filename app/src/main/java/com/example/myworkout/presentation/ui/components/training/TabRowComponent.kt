package com.example.myworkout.presentation.ui.components.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.domain.model.MuscleGroupModel

@Composable
fun TabRowComponent(
    muscleGroups: List<MuscleGroupModel>,
    onItemSelected: (item: MuscleGroupModel) -> Unit,
) {
    if (muscleGroups.isNotEmpty()) {
        val selectedTabIndex = remember { mutableIntStateOf(0) }
        val muscleGroupItem = remember { mutableStateOf(muscleGroups[0]) }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 80.dp)) {
            SetScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                muscleGroups = muscleGroups,
                onTabClick = { item, index ->
                    muscleGroupItem.value = item
                    selectedTabIndex.value = index
                    onItemSelected(item)
                }
            )
        }
    }
}

@Composable
private fun SetScrollableTabRow(
    selectedTabIndex: MutableIntState,
    muscleGroups: List<MuscleGroupModel>,
    onTabClick: (item: MuscleGroupModel, index: Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex.value,
        backgroundColor = Color(0x2A8D8D8D),
        edgePadding = 4.dp,
        tabs = {
            muscleGroups.forEachIndexed { index, muscleGroup ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { onTabClick(muscleGroup, index) },
                    text = { Text(muscleGroup.name) },
                    modifier = Modifier.width(120.dp)
                )
            }
        }
    )
}

@Preview
@Composable
private fun TabRowComponentPreview() {
    TabRowComponent(
        muscleGroups = Constants().groupsMock,
        onItemSelected = {},
    )
}