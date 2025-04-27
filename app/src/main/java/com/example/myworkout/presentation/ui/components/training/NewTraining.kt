package com.example.myworkout.presentation.ui.components.training

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.extensions.setImageDrawable

// Todo - Seção TabRowComponent
// Todo - Imagem
// Todo - Seção Chips




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
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        Image(
            painter = painterResource( bodyPartImage.setImageDrawable()),
            contentDescription = null,
        )
    }
}

@Composable
fun ChipsSection() {
}

