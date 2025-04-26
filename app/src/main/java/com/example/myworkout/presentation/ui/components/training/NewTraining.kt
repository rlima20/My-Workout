package com.example.myworkout.presentation.ui.components.training

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myworkout.domain.model.MuscleGroupModel

// Todo - Seção TabRowComponent
// Todo - Imagem
// Todo - Seção Chips
@Composable
fun NewTraining(muscleGroups: List<MuscleGroupModel> = emptyList()) {
    Column(Modifier.fillMaxSize()) {
        TabRowSection(muscleGroups)
        ImageSection()
        ChipsSection()
    }
}

@Composable
fun TabRowSection(muscleGroups: List<MuscleGroupModel>) {
    TabRowComponent(muscleGroups)
}

@Composable
fun ImageSection() {
}

@Composable
fun ChipsSection() {
}

