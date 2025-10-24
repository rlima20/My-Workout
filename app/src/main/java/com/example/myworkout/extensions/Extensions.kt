package com.example.myworkout.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.myworkout.Constants.Companion.DEFAULT_PADDING
import com.example.myworkout.Constants.Companion.FRIDAY
import com.example.myworkout.Constants.Companion.MONDAY
import com.example.myworkout.Constants.Companion.SATURDAY
import com.example.myworkout.Constants.Companion.SUNDAY
import com.example.myworkout.Constants.Companion.THURSDAY
import com.example.myworkout.Constants.Companion.TUESDAY
import com.example.myworkout.Constants.Companion.WEDNESDAY
import com.example.myworkout.R
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

fun DayOfWeek.toPortugueseString(): String =
    when (this) {
        DayOfWeek.MONDAY -> MONDAY
        DayOfWeek.TUESDAY -> TUESDAY
        DayOfWeek.WEDNESDAY -> WEDNESDAY
        DayOfWeek.THURSDAY -> THURSDAY
        DayOfWeek.FRIDAY -> FRIDAY
        DayOfWeek.SATURDAY -> SATURDAY
        DayOfWeek.SUNDAY -> SUNDAY
    }

fun String.toDayOfWeekOrNull(): DayOfWeek? = when (this) {
    MONDAY -> DayOfWeek.MONDAY
    TUESDAY -> DayOfWeek.TUESDAY
    WEDNESDAY -> DayOfWeek.WEDNESDAY
    THURSDAY -> DayOfWeek.THURSDAY
    FRIDAY -> DayOfWeek.FRIDAY
    SATURDAY -> DayOfWeek.SATURDAY
    SUNDAY -> DayOfWeek.SUNDAY
    else -> null
}

@Composable
fun Status.setBackGroundColor(): Int =
    when (this) {
        Status.PENDING -> R.color.pending
        Status.ACHIEVED -> R.color.achieved
        Status.MISSED -> R.color.missed
        Status.EMPTY -> R.color.empty
    }

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.homeScreenCardPaddings(): Modifier = composed {
    this.padding(
        top = 72.dp,
        start = 16.dp,
        end = 16.dp,
        bottom = 72.dp
    )
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.trainingCardFilterChipListModifier(): Modifier = composed {
    this
        .fillMaxWidth()
        .padding(start = DEFAULT_PADDING, end = DEFAULT_PADDING)
}