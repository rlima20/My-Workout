package com.example.myworkout.presentation.viewmodel

import android.util.Log
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
import kotlin.collections.filter

class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<MuscleGroupViewState> =
        MutableStateFlow(MuscleGroupViewState.InitialState)
    val viewState: StateFlow<MuscleGroupViewState> get() = _viewState

    private val _muscleGroups: MutableStateFlow<List<MuscleGroupModel>> = MutableStateFlow(listOf())
    val muscleGroups: StateFlow<List<MuscleGroupModel>> get() = _muscleGroups

    private val _muscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val muscleSubGroups: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroups

    private val _objSelected: MutableStateFlow<Pair<Int,Boolean>> = MutableStateFlow(Pair(0, false))
    val objSelected: MutableStateFlow<Pair<Int, Boolean>> get() = _objSelected


    fun dispatchViewAction(viewAction: MuscleGroupViewAction) {
        when (viewAction) {
            is MuscleGroupViewAction.SetupInitialState -> { setInitialViewState() }
            is MuscleGroupViewAction.CreateInitialDatabase -> { createInitialDatabase(viewAction.isFirstInstall) }
            is MuscleGroupViewAction.CreateMuscleGroup -> { createMuscleGroup(viewAction.name, BodyPart.OTHER) }
            is MuscleGroupViewAction.CreateMuscleSubGroup -> { createMuscleSubGroup(viewAction.name) }
            is MuscleGroupViewAction.FetchMuscleGroups -> { fetchMuscleGroups() }
            is MuscleGroupViewAction.FetchMuscleSubGroups -> { fetchMuscleSubGroups() }
            is MuscleGroupViewAction.SaveGroupSubGroupRelation -> { insertMuscleGroupMuscleSubGroup(viewAction.newList) }
            is MuscleGroupViewAction.ClearGroupsAndSubGroupsSelected -> { clearSubGroups() }
            is MuscleGroupViewAction.UpdateSubGroup -> { updateSubGroup(viewAction.subGroup) }
            is MuscleGroupViewAction.UpdateObjSelected -> { setMuscleGroups(viewAction.objSelected) }
        }
    }

    private fun fetchMuscleGroups() {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val muscleGroups = muscleGroupUseCase.getMuscleGroups()
                setMuscleGroups(muscleGroups)
                setSuccessState(MuscleGroupViewState.SuccessFetchMuscleGroups)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun fetchMuscleSubGroups() {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val muscleSubGroups = muscleGroupUseCase.getMuscleSubGroups()
                setListOfMuscleSubGroups(muscleSubGroups)
                setSuccessState(MuscleGroupViewState.SuccessFetchMuscleSubGroups)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroups: MutableList<MuscleGroupMuscleSubGroupModel>
    ) {
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                muscleGroupMuscleSubGroups.forEach {
                    muscleGroupUseCase.insertMuscleGroupMuscleSubGroup(it)
                }
                setMuscleGroups(Pair(0, false))
                setSuccessState(MuscleGroupViewState.SuccessInsertMuscleGroupMuscleSubGroup)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun createInitialDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            setLoadingState()
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    insertMuscleSubGroups()
                    setSuccessState(MuscleGroupViewState.SuccessDatabaseCreated)
                } catch (exception: Exception) {
                    setErrorState(exception.message.toString())
                }
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
            try {
                    muscleGroupUseCase.insertMuscleGroup(muscleGroup)
                    setSuccessState(MuscleGroupViewState.SuccessInsertMuscleGroup)
                    fetchMuscleGroups()
                    fetchMuscleSubGroups()
                    clearSubGroups()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        setLoadingState()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                muscleGroupUseCase.insertMuscleSubGroup(muscleSubGroup)
                setSuccessState(MuscleGroupViewState.SuccessInsertMuscleSubGroup)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun createMuscleGroup(name: String, image: BodyPart) {
        viewModelScope.launch(Dispatchers.IO) {
            insertMuscleGroup(
                MuscleGroupModel(
                    muscleGroupId = muscleGroupUseCase.getMuscleGroups().size + 1,
                    name = name,
                    image = image
                )
            )
        }
    }

    private fun createMuscleSubGroup(name: String) {
        insertMuscleSubGroup(
            MuscleSubGroupModel(
                name = name
            )
        )
    }

    private fun clearSubGroups() {
        setLoadingState()

        val subGroupsSelected: List<MuscleSubGroupModel> =
            _muscleSubGroups.value.filter { it.selected }
        val muscleGroupSubGroups: MutableList<MuscleSubGroupModel> = mutableListOf()

        subGroupsSelected.forEach { subGroup ->
            muscleGroupSubGroups.add(
                subGroup.copy(selected = false)
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                muscleGroupSubGroups.forEach { subGroup ->
                    updateSubGroup(subGroup)
                }
                setSuccessState(MuscleGroupViewState.InitialState)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun updateSubGroup(subGroup: MuscleSubGroupModel){
        setLoadingState()
        viewModelScope.launch(Dispatchers.IO) {
            try{
                muscleGroupUseCase.updateSubGroup(subGroup)
                setSuccessState(MuscleGroupViewState.InitialState)
            }catch (exception: Exception){
                setErrorState(exception.message.toString())
            }
        }

    }

    private fun setMuscleGroups(objSelected: Pair<Int, Boolean>){
        _objSelected.value = objSelected
    }

    private fun setMuscleGroups(value: List<MuscleGroupModel>) {
        _muscleGroups.value = value
    }

    private fun setListOfMuscleSubGroups(muscleSubGroups: List<MuscleSubGroupModel>) {
        _muscleSubGroups.value = muscleSubGroups
    }

    private fun setInitialViewState() {
        _viewState.value = MuscleGroupViewState.InitialState
    }

    private fun setLoadingState() {
        _viewState.value = MuscleGroupViewState.Loading
    }

    private fun setSuccessState(state: MuscleGroupViewState) {
        _viewState.value = state
    }

    private fun setErrorState(exception: String) {
        Log.e(EXCEPTION, exception)
        _viewState.value = MuscleGroupViewState.Error
    }

    companion object{
        const val EXCEPTION = "Exception"
    }
}