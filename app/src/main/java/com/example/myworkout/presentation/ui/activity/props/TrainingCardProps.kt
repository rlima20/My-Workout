package com.example.myworkout.presentation.ui.activity.props

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class TrainingCardProps(
    val modifier: Modifier,
    val topBarHeight: Dp,
    val chipHeight: Dp,
    val cardHeight: Dp?,
    val trainingNameFontSize: TextUnit,
)
