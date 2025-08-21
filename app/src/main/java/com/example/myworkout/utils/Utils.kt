package com.example.myworkout.utils

import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.myworkout.R
import com.example.myworkout.enums.Status

class Utils {
    fun setStatus(
        isTrainingChecked: Boolean,
        trainingStatus: Status,
        firstStatus: Status
    ) = if (isTrainingChecked) Status.ACHIEVED
    else
        when (trainingStatus) {
            Status.MISSED -> {
                firstStatus
            }

            Status.EMPTY -> {
                Status.EMPTY
            }

            else -> {
                Status.PENDING
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
}

@Composable
fun getCardColors(): CardColors = CardDefaults.cardColors(
    containerColor = colorResource(R.color.top_bar_color),
    contentColor = colorResource(R.color.top_bar_color),
    disabledContainerColor = colorResource(R.color.top_bar_color),
    disabledContentColor = colorResource(R.color.top_bar_color)
)