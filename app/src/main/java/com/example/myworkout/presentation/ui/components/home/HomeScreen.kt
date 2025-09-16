package com.example.myworkout.presentation.ui.components.home

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.Status
import com.example.myworkout.presentation.ui.components.trainingcard.TrainingCard

@RequiresApi(35)
@Composable
internal fun HomeScreen(
    trainingAndSubGroups: List<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>>,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onGetMuscleSubGroupsByTrainingId: (training: Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyHorizontalGrid(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            rows = GridCells.Adaptive(225.dp)
        ) {
            items(trainingAndSubGroups.size) { index ->
                TrainingCard(
                    modifier = Modifier.size(150.dp, 180.dp),
                    training = trainingAndSubGroups[index].first,
                    muscleSubGroupList = trainingAndSubGroups[index].second,
                    isFilterChipListEnabled = false,
                    onMuscleGroupSelected = {},
                    onAddButtonClicked = {}, // Todo - Navega para a tela de novo treinamento
                    onTrainingChecked = { onTrainingChecked(it) },
                    onGetMuscleSubGroupsByTrainingId = { onGetMuscleSubGroupsByTrainingId(it) }
                )
            }
        }
    }
}

@RequiresApi(35)
@Composable
@Preview
fun HomeScreenPreview() {

    val listOfTrainingAndSubGroups = listOf(
        Pair(
            Constants().trainingMock(Status.ACHIEVED, "Peito e Tríceps"),
            Constants().chestAndTricepsSubGroups
        ),
        Pair(
            Constants().trainingMock(Status.PENDING, "Ombro"),
            Constants().shoulderSubGroups
        ),
        Pair(
            Constants().trainingMock(Status.EMPTY, "Bíceps e Antebraço"),
            Constants().bicepsSubGroups
        )
    )

    HomeScreen(
        trainingAndSubGroups = listOfTrainingAndSubGroups,
        onTrainingChecked = {},
        onGetMuscleSubGroupsByTrainingId = {}
    )
}