package com.example.myworkout.utils

import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.myworkout.Constants.Companion.FRIDAY
import com.example.myworkout.Constants.Companion.MONDAY
import com.example.myworkout.Constants.Companion.SATURDAY
import com.example.myworkout.Constants.Companion.SUNDAY
import com.example.myworkout.Constants.Companion.THURSDAY
import com.example.myworkout.Constants.Companion.TUESDAY
import com.example.myworkout.Constants.Companion.WEDNESDAY
import com.example.myworkout.R
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status

class Utils {

    fun mapDayOfWeekToNumber(dayOfWeek: DayOfWeek): Int =
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
            DayOfWeek.SUNDAY -> 7
        }

    fun weekToString(dayOfWeek: DayOfWeek): String =
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> MONDAY
            DayOfWeek.TUESDAY -> TUESDAY
            DayOfWeek.WEDNESDAY -> WEDNESDAY
            DayOfWeek.THURSDAY -> THURSDAY
            DayOfWeek.FRIDAY -> FRIDAY
            DayOfWeek.SATURDAY -> SATURDAY
            DayOfWeek.SUNDAY -> SUNDAY
        }

    fun stringToWeek(dayOfWeek: String): DayOfWeek =
        when (dayOfWeek) {
            MONDAY -> DayOfWeek.MONDAY
            TUESDAY -> DayOfWeek.TUESDAY
            WEDNESDAY -> DayOfWeek.WEDNESDAY
            THURSDAY -> DayOfWeek.THURSDAY
            FRIDAY -> DayOfWeek.FRIDAY
            SATURDAY -> DayOfWeek.SATURDAY
            SUNDAY -> DayOfWeek.SUNDAY
            else -> {}
        } as DayOfWeek

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

    @Composable
    fun buttonColors(): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(R.color.white),
        contentColor = colorResource(R.color.white),
        disabledBackgroundColor = colorResource(R.color.white),
        disabledContentColor = colorResource(R.color.white)

    )
}
