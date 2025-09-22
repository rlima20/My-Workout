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
import com.example.myworkout.R
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.Status

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

fun BodyPart.setImageDrawable(): Int =
    when (this) {
        BodyPart.LEG -> {
            R.drawable.pernas
        }

        BodyPart.BACK -> {
            R.drawable.costas
        }

        BodyPart.ARM -> {
            R.drawable.braco
        }

        BodyPart.CHEST -> {
            R.drawable.peito
        }

        BodyPart.ABDOMEN -> {
            R.drawable.abdomem
        }

        BodyPart.SHOULDER -> {
            R.drawable.ombro
        }

        BodyPart.TRAPEZIUS -> {
            R.drawable.trapezio
        }

        BodyPart.OTHER -> {
            R.drawable.ombro
        }
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