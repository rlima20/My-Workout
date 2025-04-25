package com.example.myworkout.presentation.viewmodel

sealed class TrainingViewAction {
    object FetchTrainings : TrainingViewAction()
    object CreateTrainings : TrainingViewAction()
}