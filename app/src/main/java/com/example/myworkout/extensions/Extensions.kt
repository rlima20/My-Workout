package com.example.myworkout.extensions

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
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
    when(this){
        BodyPart.LEG -> {
            R.drawable.pernas}
        BodyPart.BACK -> {
            R.drawable.costas}
        BodyPart.ARM -> {
            R.drawable.braco}
        BodyPart.CHEST -> {
            R.drawable.peito}
        BodyPart.ABDOMEN -> {
            R.drawable.abdomem}
        BodyPart.SHOULDER -> {
            R.drawable.ombro}
        BodyPart.TRAPEZIUS -> {
            R.drawable.trapezio}
        BodyPart.OTHER -> {
            R.drawable.ombro
        }
    }

fun String.emptyString(): String = ""
