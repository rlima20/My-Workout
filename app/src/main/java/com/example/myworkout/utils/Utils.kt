package com.example.myworkout.utils

import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.myworkout.R
import com.example.myworkout.enums.Status

fun setStatus(
    isTrainingChecked: Boolean,
    trainingStatus: Status,
    firstStatus: Status
) = if (isTrainingChecked) {
    Status.ACHIEVED
} else {
    when (trainingStatus) {
        Status.MISSED -> { firstStatus }
        Status.EMPTY -> { Status.EMPTY }
        else -> { Status.PENDING }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun selectableChipColors() = ChipDefaults.filterChipColors(
    backgroundColor = colorResource(R.color.content),
    selectedContentColor = colorResource(R.color.button_color),
    disabledBackgroundColor = colorResource(R.color.content),
    disabledContentColor = colorResource(R.color.pending),
    selectedBackgroundColor = colorResource(R.color.button_color)
)