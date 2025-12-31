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
import androidx.compose.ui.unit.dp
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.presentation.ui.activity.props.TrainingCardProps
import com.example.myworkout.presentation.viewmodel.MuscleGroupViewModel
import com.example.myworkout.presentation.viewmodel.TrainingViewModel
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
    val initialPage = rememberInitialPage(workouts)

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { workouts.size }
    )

    LaunchedEffect(initialPage) {
        pagerState.scrollToPage(initialPage)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
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
                .padding(top = 16.dp, bottom = 80.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(workouts.size) { index ->
                DotIndicator(
                    isSelected = pagerState.currentPage == index
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun rememberInitialPage(
    workouts: List<Pair<TrainingModel, List<SubGroupModel>>>
): Int {
    val currentDay = getCurrentDayOfWeek()

    return workouts.indexOfFirst {
        it.first.dayOfWeek.toPortugueseString().lowercase() == currentDay
    }.takeIf { it >= 0 } ?: 0
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCurrentDayOfWeek(): String =
    LocalDate.now()
        .dayOfWeek
        .getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
        .substringBefore("-")
