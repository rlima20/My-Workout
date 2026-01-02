package com.example.nutrition.mynutrition.extensions

import com.example.nutrition.mynutrition.domain.model.macro.MacroUiModel

fun MacroUiModel.grams(): Int {
    return (kcal / type.kcalPerGram.toFloat()).toInt()
}