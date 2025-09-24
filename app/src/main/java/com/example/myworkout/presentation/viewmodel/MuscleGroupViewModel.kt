package com.example.myworkout.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.Constants.Companion.MUSCLE_SUB_GROUP_NAMES
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.viewmodel.viewaction.MuscleGroupViewAction
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private val _viewState: MutableStateFlow<MuscleGroupViewState> =
        MutableStateFlow(MuscleGroupViewState.InitialState)
    val viewState: StateFlow<MuscleGroupViewState> get() = _viewState

    private val _muscleGroups: MutableStateFlow<List<MuscleGroupModel>> = MutableStateFlow(listOf())
    val muscleGroups: StateFlow<List<MuscleGroupModel>> get() = _muscleGroups

    private val _muscleGroupsWithRelation: MutableStateFlow<List<MuscleGroupModel>> =
        MutableStateFlow(listOf())
    val muscleGroupsWithRelation: StateFlow<List<MuscleGroupModel>> get() = _muscleGroupsWithRelation

    private val _muscleSubGroups: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val muscleSubGroups: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroups

    private val _muscleSubGroupsByTraining: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val muscleSubGroupsByTraining: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroupsByTraining

    private val _relations: MutableStateFlow<List<MuscleGroupMuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val relations: StateFlow<List<MuscleGroupMuscleSubGroupModel>> get() = _relations

    private val _objSelected: MutableStateFlow<Pair<Int, Boolean>> =
        MutableStateFlow(Pair(0, false))
    val objSelected: StateFlow<Pair<Int, Boolean>> get() = _objSelected


    private val _workouts: MutableStateFlow<MutableList<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>>> =
        MutableStateFlow(
            mutableListOf()
        )

    val workouts: StateFlow<MutableList<Pair<TrainingModel, MutableList<MuscleSubGroupModel>>>> = _workouts

    fun dispatchViewAction(viewAction: MuscleGroupViewAction) {
        when (viewAction) {
            is MuscleGroupViewAction.SetupInitialState -> {
                setInitialViewState()
            }

            is MuscleGroupViewAction.CreateInitialDatabase -> {
                createInitialDatabase(viewAction.isFirstInstall)
            }

            is MuscleGroupViewAction.CreateMuscleGroup -> {
                insertMuscleGroup(viewAction.name, BodyPart.OTHER)
            }

            is MuscleGroupViewAction.CreateMuscleSubGroup -> {
                insertMuscleSubGroup(MuscleSubGroupModel(name = viewAction.name))
            }

            is MuscleGroupViewAction.FetchMuscleGroups -> {
                fetchMuscleGroups()
            }

            is MuscleGroupViewAction.FetchMuscleSubGroups -> {
                fetchMuscleSubGroups()
            }

            is MuscleGroupViewAction.SaveGroupSubGroupRelation -> {
                insertMuscleGroupMuscleSubGroup(viewAction.newList)
            }

            is MuscleGroupViewAction.ClearGroupsAndSubGroupsSelected -> {
                clearSubGroups()
            }

            is MuscleGroupViewAction.UpdateSubGroup -> {
                updateSubGroup(viewAction.subGroup)
            }

            is MuscleGroupViewAction.UpdateObjSelected -> {
                setMuscleGroupSelected(viewAction.objSelected)
            }

            is MuscleGroupViewAction.GetSubGroupsByTraining -> {
                getSubgroupsByTraining(viewAction.training)
            }

            is MuscleGroupViewAction.FetchRelations -> {
                getGroupsWithRelations()
            }
        }
    }

    private fun getGroupsWithRelations() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val groupsWithRelation = muscleGroupUseCase.getMuscleGroupsWithRelations()
                _muscleGroupsWithRelation.value = groupsWithRelation
                setSuccessState(
                    MuscleGroupViewState.SuccessGetGroupsWithRelations(
                        _muscleGroupsWithRelation.value
                    )
                )
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun fetchMuscleGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val muscleGroups = muscleGroupUseCase.getMuscleGroups()
                setMuscleGroupsSelected(muscleGroups)
                setSuccessState(MuscleGroupViewState.SuccessFetchMuscleGroups)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun fetchMuscleSubGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val muscleSubGroups = muscleGroupUseCase.getMuscleSubGroups()
                setListOfMuscleSubGroups(muscleSubGroups)
                setSuccessState(MuscleGroupViewState.SuccessFetchMuscleSubGroups)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun getSubgroupsByTraining(training: TrainingModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val subGroups = muscleGroupUseCase
                    .getMuscleSubGroupsByTrainingId(
                        training.trainingId
                    )

                _muscleSubGroupsByTraining.value = subGroups








                setSuccessState(MuscleGroupViewState.SuccessGetSubGroupsByTraining)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroups: MutableList<MuscleGroupMuscleSubGroupModel>
    ) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupMuscleSubGroups.forEach {
                    muscleGroupUseCase.insertMuscleGroupMuscleSubGroup(it)
                }
                setMuscleGroupSelected(Pair(0, false))
                setSuccessState(MuscleGroupViewState.SuccessInsertMuscleGroupMuscleSubGroup)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun createInitialDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            setLoadingState()
            viewModelScope.launch(dispatchers.IO) {
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
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, name ->
            insertMuscleSubGroup(
                MuscleSubGroupModel(
                    id = index + 1,
                    name = name
                )
            )
        }
    }

    private fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.insertMuscleSubGroup(muscleSubGroup)
                setSuccessState(MuscleGroupViewState.SuccessInsertMuscleSubGroup)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun insertMuscleGroup(name: String, image: BodyPart) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                muscleGroupUseCase.insertMuscleGroup(name, image)
                setSuccessState(MuscleGroupViewState.SuccessInsertMuscleGroup)
                fetchMuscleGroups()
                fetchMuscleSubGroups()
                clearSubGroups()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun clearSubGroups() {
        val subGroupsSelected: List<MuscleSubGroupModel> =
            _muscleSubGroups.value.filter { it.selected }
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.clearSelectedMuscleSubGroups(subGroupsSelected)
                setSuccessState(MuscleGroupViewState.InitialState)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.updateSubGroup(subGroup)
                setSuccessState(MuscleGroupViewState.InitialState)
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    private fun setMuscleGroupSelected(objSelected: Pair<Int, Boolean>) {
        _objSelected.value = objSelected
    }

    private fun setMuscleGroupsSelected(value: List<MuscleGroupModel>) {
        _muscleGroups.value = value
    }

    private fun setListOfMuscleSubGroups(muscleSubGroups: List<MuscleSubGroupModel>) {
        _muscleSubGroups.value = muscleSubGroups
    }

    private fun setInitialViewState() {
        _viewState.value = MuscleGroupViewState.InitialState
    }

    private fun setLoadingState() {
        Log.e(LOADING, LOADING)
        _viewState.value = MuscleGroupViewState.Loading
    }

    private fun setSuccessState(state: MuscleGroupViewState) {
        Log.e(SUCCESS, state.javaClass.name.toString())
        _viewState.value = state
    }

    private fun setErrorState(exception: String) {
        Log.e(EXCEPTION, exception)
        _viewState.value = MuscleGroupViewState.Error
    }

    companion object {
        const val EXCEPTION = "Exception"
        const val SUCCESS = "Success"
        const val LOADING = "Loading"
    }
}