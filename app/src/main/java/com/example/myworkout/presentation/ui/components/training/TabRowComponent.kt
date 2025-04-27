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
import androidx.compose.ui.unit.dp
import com.example.myworkout.domain.model.MuscleGroupModel

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun TabRowComponent(
    muscleGroups: List<MuscleGroupModel> = emptyList(),
    onCreateImageSection: () -> Unit
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
            onCreateImageSection = onCreateImageSection
        )
    }
}

@Composable
private fun ValidateDataForScrollableTabRow(
    selectedTabIndex: MutableIntState,
    muscleGroupList: MutableState<List<MuscleGroupModel>>,
    muscleGroups: List<MuscleGroupModel>,
    muscleGroupItem: MutableState<MuscleGroupModel>,
    onCreateImageSection: () -> Unit
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
            onCreateImageSection = onCreateImageSection
        )
    } else {
        Text("Conteúdo não disponível") // Todo - Modificar o componente de erro para ser um erro genérico e usar aqui
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
    onCreateImageSection: () -> Unit
) {
    TabContent(
        innerMuscleGroup = muscleGroupItem,
        onCreateImageSection = onCreateImageSection
    )
}

// Todo - Aqui eu vou ter a imagem e os CHips com os subgrupos
@Composable
fun TabContent(
    innerMuscleGroup: MuscleGroupModel,
    onCreateImageSection: () -> Unit
) {
    Text(innerMuscleGroup.name)
    onCreateImageSection()
}

