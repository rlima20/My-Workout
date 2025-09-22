package com.example.myworkout.utils

import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
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
        disabledBackgroundColor = colorResource(R.color.button_section_card_color),
        disabledContentColor = colorResource(R.color.pending),
        selectedBackgroundColor = colorResource(R.color.button_color)
    )

    @Composable
    fun getCardColors(): CardColors = CardDefaults.cardColors(
        containerColor = colorResource(R.color.title_color),
        contentColor = colorResource(R.color.title_color),
        disabledContainerColor = colorResource(R.color.title_color),
        disabledContentColor = colorResource(R.color.title_color)
    )

    @Composable
    fun buttonSectionCardsColors(): CardColors = CardDefaults.cardColors(
        containerColor = colorResource(R.color.button_section_card_color),
        contentColor = colorResource(R.color.button_section_card_color),
        disabledContainerColor = colorResource(R.color.button_section_card_color),
        disabledContentColor = colorResource(R.color.button_section_card_color)
    )
}

val DEFAULT_PADDING = 8.dp
val LAZY_VERTICAL_GRID_SPACING = 16.dp
val LAZY_VERTICAL_GRID_MIN_SIZE = 140.dp
val FILTER_CHIP_LIST_PADDING_BOTTOM = 8.dp
val TRAINING_CARD_PADDING_BOTTOM = 16.dp
val TRAINING_NAME_MAX_HEIGHT = 30.dp
val SUB_GROUP_SECTION_BACKGROUND = R.color.button_section_card_color



