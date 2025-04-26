package com.example.myworkout.presentation.ui.components.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myworkout.domain.model.MuscleGroupModel

@Composable
fun TabRowComponent(
    muscleGroups: List<MuscleGroupModel> = emptyList()
) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    var muscleGroupList = remember { mutableStateOf(muscleGroups) }
    var muscleGroupItem = remember { mutableStateOf(muscleGroups[0]) }

    Column(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            top = 80.dp
        )
    ) {
        if (selectedTabIndex.value in muscleGroupList.value.indices) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex.value,
                backgroundColor = Color(0x2A8D8D8D),
                edgePadding = 4.dp,
                tabs = {
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
            )
            TabContent(
                // selectedTabIndex = selectedTabIndex.value,
                innerMuscleGroup = muscleGroupItem.value
            )
        } else {
            // Todo - Modificar o componente de erro para ser um erro genérico e usar aqui
            Text("Conteúdo não disponível")
        }
    }
}

// Todo - Aqui eu vou ter a imagem e os CHips com os subgrupos
@Composable
fun TabContent(innerMuscleGroup: MuscleGroupModel) {
    Text(innerMuscleGroup.name)
}

