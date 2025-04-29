package com.example.myworkout.presentation.ui.components.training

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.myworkout.enums.Orientation
import com.example.myworkout.extensions.setImageDrawable
import com.example.myworkout.presentation.ui.components.trainingcard.FilterChipList

@SuppressLint("UnrememberedMutableState")
@Composable
fun NewTraining(muscleGroups: List<MuscleGroupModel> = emptyList()) {
    val image = remember { mutableStateOf(BodyPart.LEG) }

    Column(Modifier.fillMaxSize()) {
        TabRowSection(
            muscleGroups = muscleGroups,
            onCreateImageSection = { image.value = it }
        )
        ImageSection(image.value)
        ChipsSection()
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

// Todo - Seção Chips
@Composable
fun ChipsSection() {
    FilterChipList(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .height(50.dp),
        listOfMuscleSubGroup = mutableListOf(
            MuscleSubGroupModel(
                id = 1,
                name = "Superior"
            ),
            MuscleSubGroupModel(
                id = 1,
                name = "Inferior"
            ),
            MuscleSubGroupModel(
                id = 1,
                name = "Posterior"
            )
        ),
        onItemClick = {},
        backGroundColor = R.color.white,
        orientation = Orientation.HORIZONTAL
    )
}


@Preview
@Composable
fun NewTrainingPreview() {
    NewTraining(Constants().muscleGroups())
}

