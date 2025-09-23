package com.example.myworkout.utils

import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.FRIDAY
import com.example.myworkout.Constants.Companion.MONDAY
import com.example.myworkout.Constants.Companion.SATURDAY
import com.example.myworkout.Constants.Companion.SUNDAY
import com.example.myworkout.Constants.Companion.THURSDAY
import com.example.myworkout.Constants.Companion.TUESDAY
import com.example.myworkout.Constants.Companion.WEDNESDAY
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
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

    fun mapDayOfWeekToString(dayOfWeek: DayOfWeek): String =
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> MONDAY
            DayOfWeek.TUESDAY -> TUESDAY
            DayOfWeek.WEDNESDAY -> WEDNESDAY
            DayOfWeek.THURSDAY -> THURSDAY
            DayOfWeek.FRIDAY -> FRIDAY
            DayOfWeek.SATURDAY -> SATURDAY
            DayOfWeek.SUNDAY -> SUNDAY
        }

    fun sortTrainingsByDayOfWeek(
        trainings: List<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>>
    ): List<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>> {
        return trainings.sortedBy { (training, _) ->
            Utils().mapDayOfWeekToNumber(training.dayOfWeek)
        } as MutableList<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>>
    }

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
