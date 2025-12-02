package com.example.myworkout.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel

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

    @Composable
    fun buttonSectionCardsDisabledColors(): CardColors = CardDefaults.cardColors(
        containerColor = colorResource(R.color.empty),
        contentColor = colorResource(R.color.empty),
        disabledContainerColor = colorResource(R.color.empty),
        disabledContentColor = colorResource(R.color.empty)
    )

    @Composable
    fun getTextFieldColors(): TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedLabelColor = colorResource(R.color.text_color),
        cursorColor = colorResource(R.color.text_color),
        backgroundColor = colorResource(R.color.text_field_background_color),
        textColor = colorResource(R.color.text_color),
        focusedIndicatorColor = colorResource(R.color.title_color)
    )

    @Composable
    fun setModifier(isCardSectionVisible: Boolean): Modifier =
        if (isCardSectionVisible) Modifier
        else Modifier.padding(bottom = 76.dp)

    @Composable
    fun setSelectedItem(objSelected: Pair<Int, Boolean>, muscleGroup: MuscleGroupModel) =
        if (objSelected.first == muscleGroup.muscleGroupId) objSelected.second else muscleGroup.selected

    fun verifyEnabledButton(muscleSubGroupsSelected: List<MuscleSubGroupModel>): Boolean {
        return muscleSubGroupsSelected.any { it.selected }
    }

    @Composable
    fun getLabel(trainingName: String): String =
        if (trainingName.isEmpty()) stringResource(R.string.new_training_input_text_label)
        else Constants().emptyString()

    fun setEnabled(
        trainingsQuantity: Int,
        maxDaysQuantity: Int,
        string: String
    ): Boolean = if (trainingsQuantity <= maxDaysQuantity) true else string.isNotEmpty()
}
