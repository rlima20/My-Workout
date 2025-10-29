package com.example.myworkout.utils

import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.myworkout.R

class Utils {
    @Composable
    @OptIn(ExperimentalMaterialApi::class)
    fun selectableChipColors() = ChipDefaults.filterChipColors(
        backgroundColor = colorResource(R.color.filter_chip_disabled_color),
        selectedContentColor = colorResource(R.color.button_color),
        disabledBackgroundColor = colorResource(R.color.filter_chip_disabled_color),
        disabledContentColor = colorResource(R.color.filter_chip_disabled_color),
        selectedBackgroundColor = colorResource(R.color.button_color)
    )

    @Composable
    fun getCardColors(): CardColors = CardDefaults.cardColors(
        containerColor = colorResource(R.color.training_section_card_color),
        contentColor = colorResource(R.color.training_section_card_color),
        disabledContainerColor = colorResource(R.color.training_section_card_color),
        disabledContentColor = colorResource(R.color.training_section_card_color)
    )

    @Composable
    fun buttonSectionCardsColors(): CardColors = CardDefaults.cardColors(
        containerColor = colorResource(R.color.white),
        contentColor = colorResource(R.color.white),
        disabledContainerColor = colorResource(R.color.white),
        disabledContentColor = colorResource(R.color.white)
    )
}
