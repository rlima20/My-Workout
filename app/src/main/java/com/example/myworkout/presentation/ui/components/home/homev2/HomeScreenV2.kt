package com.example.myworkout.presentation.ui.components.home.homev2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT_V2
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModelFake
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModelFake
import com.example.onboarding.ui.Components.DotIndicator
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun HomeScreenV2(
    modifier: Modifier,
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    viewModel: TrainingViewModel,
    muscleGroupViewModel: MuscleGroupViewModel,
    trainingCardProps: TrainingCardProps
) {
    var initialPage = getIndex(workouts)
    initialPage = setIndex(workouts, initialPage)

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { workouts.size }
    )

    LaunchedEffect(initialPage) {
        pagerState.scrollToPage(initialPage)
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            PagerScreen(
                workout = workouts[pageIndex],
                listOfDays = listOfDays,
                viewModel = viewModel,
                trainingCardProps = trainingCardProps,
                muscleGroupViewModel = muscleGroupViewModel
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(workouts.size) { index ->
                DotIndicator(isSelected = pagerState.currentPage == index)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun setIndex(
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>,
    initialPage: Int
): Int {
    var innerInitialPage = initialPage
    workouts.forEachIndexed { index, workout ->
        val dayOfWeek = workout.first.dayOfWeek.toPortugueseString().lowercase()
        if (dayOfWeek == getCurrentDayOfWeek()) {
            innerInitialPage = index
        }
    }
    return innerInitialPage
}

@RequiresApi(Build.VERSION_CODES.O)
fun getIndex(workouts: List<Pair<TrainingModel, List<SubGroupModel>>>): Int {
    var initialPage = 0

    workouts.forEachIndexed { index, workout ->
        val dayOfWeek = workout.first.dayOfWeek.toPortugueseString().lowercase()
        if (dayOfWeek == getCurrentDayOfWeek()) {
            initialPage = index
        }
    }

    return initialPage
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCurrentDayOfWeek(): String = LocalDate.now()
    .dayOfWeek
    .getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
    .substringBefore("-")

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
@Preview
private fun HomeScreenPreviewV2() {
    HomeScreenV2(
        modifier = Modifier,
        muscleGroupViewModel = MuscleGroupViewModelFake(),
        viewModel = TrainingViewModelFake(),
        listOfDays = Constants().getListOfDays(),
        workouts = Constants().getNewTrainingAndSubGroupsHomeScreenMock(),
        trainingCardProps = TrainingCardProps(
            modifier = Modifier,
            topBarHeight = TRAINING_NAME_MAX_HEIGHT_V2,
            chipHeight = 50.dp,
            cardHeight = 800.dp,
            trainingNameFontSize = 16.sp
        )
    )
}