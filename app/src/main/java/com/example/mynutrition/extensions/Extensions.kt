package com.example.mynutrition.extensions

import com.example.mynutrition.domain.model.macro.MacroUiModel

fun MacroUiModel.grams(): Int {
    return (kcal / type.kcalPerGram.toFloat()).toInt()
}