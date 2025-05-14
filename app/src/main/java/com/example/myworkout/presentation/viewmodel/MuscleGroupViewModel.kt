package com.example.myworkout.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.Constants
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.viewmodel.viewaction.MuscleGroupViewAction
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<MuscleGroupViewState> = MutableStateFlow(MuscleGroupViewState.InitialState)
    val viewState: StateFlow<MuscleGroupViewState> get() = _viewState

    private val _muscleGroups: MutableStateFlow<List<MuscleGroupModel>> = MutableStateFlow(listOf())
    val muscleGroups: StateFlow<List<MuscleGroupModel>> get() = _muscleGroups

    private val _muscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> = MutableStateFlow(listOf())
    val muscleSubGroups: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroups

    fun dispatchViewAction(viewAction: MuscleGroupViewAction) {
        when (viewAction) {
            is MuscleGroupViewAction.SetupInitialState -> { setInitialViewState() }
            is MuscleGroupViewAction.CreateInitialDatabase -> { createInitialDatabase(viewAction.isFirstInstall) }
            is MuscleGroupViewAction.CreateMuscleGroup -> { createMuscleGroup(viewAction.name, BodyPart.OTHER) }
            is MuscleGroupViewAction.CreateMuscleSubGroup -> { createMuscleSubGroup(viewAction.name) }
            is MuscleGroupViewAction.FetchMuscleGroups -> { fetchMuscleGroups() }
            is MuscleGroupViewAction.FetchMuscleSubGroups -> { fetchMuscleSubGroups() }
        }
    }

    private fun fetchMuscleGroups() {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val muscleGroups = muscleGroupUseCase.getMuscleGroups()
                setMuscleGroups(muscleGroups)
                setSuccessState()
            } catch (e: Exception) { setErrorState() }
        }
    }

    private fun fetchMuscleSubGroups() {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val muscleSubGroups = muscleGroupUseCase.getMuscleSubGroups()
                setListOfMuscleSubGroups(muscleSubGroups)
                setSuccessState()
            } catch (e: Exception) { setErrorState() }
        }
    }

    private fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroup: MuscleGroupMuscleSubGroupModel
    ) {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    muscleGroupUseCase.insertMuscleGroupMuscleSubGroup(muscleGroupMuscleSubGroup)
                    setSuccessState()
                }
            } catch (e: Exception) { setErrorState() }
        }
    }

    private fun createInitialDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            setLoadingState()
            viewModelScope.launch(Dispatchers.IO) {
                try{
                    insertMuscleSubGroups()
                    setSuccessState()
                } catch (e: Exception) { setErrorState() }
            }
        }
    }

    private fun insertMuscleSubGroups() {
        Constants().muscleSubGroupNames.forEachIndexed { index, name ->
            insertMuscleSubGroup(
                MuscleSubGroupModel(
                    id = index + 1,
                    name = name
                )
            )
        }
    }

    private fun insertMuscleGroup(muscleGroup: MuscleGroupModel) {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            try {
                viewModelScope.launch(Dispatchers.IO) {
                    muscleGroupUseCase.insertMuscleGroup(muscleGroup)
                    setSuccessState()
                }
            } catch (e: Exception) { setErrorState() }
        }
    }

    private fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        _viewState.value = MuscleGroupViewState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            try {
                muscleGroupUseCase.insertMuscleSubGroup(muscleSubGroup)
                setSuccessState()
            } catch (e: Exception) { setErrorState() }
        }
    }

    private fun createMuscleGroup(name: String, image: BodyPart) {
        insertMuscleGroup(MuscleGroupModel(name = name, image = image))
    }

    private fun createMuscleSubGroup(name: String) {
        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = name
            )
        )
    }

    private fun setMuscleGroups(value: List<MuscleGroupModel>) {
        _muscleGroups.value = value
    }

    private fun setListOfMuscleSubGroups(muscleSubGroups: List<MuscleSubGroupModel>){
        _muscleSubGroups.value = muscleSubGroups
    }

    private fun setInitialViewState() {
        _viewState.value = MuscleGroupViewState.InitialState
    }

    private fun setLoadingState() {
        _viewState.value = MuscleGroupViewState.Loading
    }

    private fun setSuccessState() {
        _viewState.value = MuscleGroupViewState.Success
    }

    private fun setErrorState() {
        _viewState.value = MuscleGroupViewState.Error
    }
}