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
    listOfMuscleSubGroup: List<MuscleSubGroupModel>
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
            items(trainingList.size) { item ->
                TrainingCard(
                    modifier = Modifier.size(150.dp, 180.dp),
                    training = trainingList[item],
                    muscleSubGroupModel = listOfMuscleSubGroup,
                    isFilterChipListEnabled = false,
                    onMuscleGroupSelected = {},
                    onAddButtonClicked = {}
                )
            }
        }
    }
}