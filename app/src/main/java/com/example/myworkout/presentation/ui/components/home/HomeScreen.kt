package com.example.myworkout.presentation.ui.components.home

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myworkout.Constants
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.Status
import com.example.myworkout.extensions.homeScreenCardPaddings
import com.example.myworkout.presentation.ui.components.trainingcard.TrainingCard
import com.example.myworkout.utils.FILTER_CHIP_LIST_PADDING_BOTTOM
import com.example.myworkout.utils.LAZY_VERTICAL_GRID_MIN_SIZE
import com.example.myworkout.utils.LAZY_VERTICAL_GRID_SPACING

@RequiresApi(35)
@Composable
internal fun HomeScreen(
    filterChipListModifier: Modifier,
    trainingAndSubGroups: List<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>>,
    onTrainingChecked: (training: TrainingModel) -> Unit,
    onGetMuscleSubGroupsByTrainingId: (training: Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.homeScreenCardPaddings(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.spacedBy(LAZY_VERTICAL_GRID_SPACING),
        columns = GridCells.Adaptive(LAZY_VERTICAL_GRID_MIN_SIZE)
    ) {
        items(trainingAndSubGroups.size) { index ->
            TrainingCard(
                filterChipListModifier = filterChipListModifier.padding(bottom = FILTER_CHIP_LIST_PADDING_BOTTOM),
                training = trainingAndSubGroups[index].first,
                subGroups = trainingAndSubGroups[index].second,
                chipListEnabled = false,
                onMuscleGroupSelected = {},
                onAddButtonClicked = {},
                onTrainingChecked = { onTrainingChecked(it) },
                onGetMuscleSubGroupsByTrainingId = { onGetMuscleSubGroupsByTrainingId(it) }
            )
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
        filterChipListModifier = Modifier,
        onTrainingChecked = {},
        onGetMuscleSubGroupsByTrainingId = {}
    )
}