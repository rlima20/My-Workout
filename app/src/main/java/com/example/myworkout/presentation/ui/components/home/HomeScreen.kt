package com.example.myworkout.presentation.ui.components.home

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.presentation.ui.components.trainingcard.TrainingCard

@RequiresApi(35)
@Composable
internal fun HomeScreen(
    trainingList: List<TrainingModel>,
    muscleSubGroupList: List<MuscleSubGroupModel>,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onGetMuscleSubGroupsByTrainingId: (training: Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.FixedSize(180.dp)
        ) {
            items(trainingList.size) { index ->
                TrainingCard(
                    modifier = Modifier.size(150.dp, 180.dp),
                    training = trainingList[index],
                    muscleSubGroupList = muscleSubGroupList,
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