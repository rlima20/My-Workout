package com.example.nutrition.mynutrition.extensions

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.nutrition.mynutrition.domain.model.macro.MacroUiModel

fun MacroUiModel.grams(): Int {
    return (kcal / type.kcalPerGram.toFloat()).toInt()
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }