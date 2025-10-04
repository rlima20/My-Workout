package com.example.myworkout.presentation.ui.components.training

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.Constants
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.extensions.setImageDrawable
import com.example.myworkout.presentation.ui.components.commons.ButtonSection
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList
import com.example.myworkout.presentation.ui.components.trainingcard.Grid
import com.example.myworkout.presentation.ui.components.trainingcard.GridProps
import com.example.myworkout.utils.Utils

@SuppressLint("UnrememberedMutableState")
@Composable
fun NewTraining(muscleGroups: List<MuscleGroupModel>) {
    val image = remember { mutableStateOf(BodyPart.LEG) }

    Column(Modifier.fillMaxSize()) {
        TabRowSection(
            muscleGroups = muscleGroups,
            onCreateImageSection = { image.value = it }
        )
        ButtonSection(
            modifier = Modifier.padding(top = 8.dp),
            titleSection = "Subgrupos",
            buttonName = "Salvar",
            buttonEnabled = true,
            onButtonClick = {},
            content = { ChipsSection(subGroups = Constants().shoulderSubGroupsMock)}
        )
    }
}

@Composable
fun TabRowSection(
    muscleGroups: List<MuscleGroupModel>,
    onCreateImageSection: @Composable (bodyPartImage: BodyPart) -> Unit
) {
    TabRowComponent(
        muscleGroups = muscleGroups,
        onCreateImageSection = { onCreateImageSection(it) }
    )
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipsSection(
    modifier: Modifier = Modifier,
    subGroups: List<MuscleSubGroupModel>
) {
    FilterChipList(
        modifier = modifier,
        backGroundColor = R.color.white,
        orientation = Grid,
        orientationProps = GridProps(
            colors = Utils().selectableChipColors(),
            listOfMuscleSubGroup = subGroups,
            enabled = false,
            onItemClick = {},
            horizontalSpacedBy = 8.dp,
            verticalSpacedBy = 1.dp,
        ),
    )
}

@Preview
@Composable
fun NewTrainingPreview() {
    NewTraining(muscleGroups = Constants().groupsMock)
}

@Composable
fun ImageSection(bodyPartImage: BodyPart) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp)
    ) {
        Image(
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(bodyPartImage.setImageDrawable()),
            contentDescription = null,
        )
    }
}


