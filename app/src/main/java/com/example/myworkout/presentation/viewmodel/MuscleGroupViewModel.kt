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
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase,
    private val muscleSubGroupUseCase: MuscleGroupUseCase,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private val _viewState: MutableStateFlow<MuscleGroupViewState> =
        MutableStateFlow(MuscleGroupViewState.Loading)
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

    private val _groupsAndSubgroupsWithRelations: MutableStateFlow<List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>> =
        MutableStateFlow(mutableListOf(mutableMapOf<MuscleGroupModel, MutableList<MuscleSubGroupModel>>()))
    val groupsAndSubgroupsWithRelations: StateFlow<List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>> get() = _groupsAndSubgroupsWithRelations

    private val _objSelected: MutableStateFlow<Pair<Int, Boolean>> =
        MutableStateFlow(Pair(0, false))
    val objSelected: StateFlow<Pair<Int, Boolean>> get() = _objSelected

    private val _workouts: MutableStateFlow<List<Pair<TrainingModel, List<MuscleSubGroupModel>>>> =
        MutableStateFlow(emptyList())
    val workouts: StateFlow<List<Pair<TrainingModel, List<MuscleSubGroupModel>>>> = _workouts

    private val _subgroupsSelected: MutableStateFlow<List<MuscleSubGroupModel>> =
        MutableStateFlow(listOf())
    val subgroupsSelected: StateFlow<List<MuscleSubGroupModel>> get() = _subgroupsSelected


    private val _selectedGroup = MutableStateFlow(getDefaultGroup())
    val selectedGroup: StateFlow<MuscleGroupModel> = _selectedGroup

    fun List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>.extractSubGroupsByGroup(
        targetGroup: MuscleGroupModel
    ): List<MuscleSubGroupModel> {
        return firstOrNull { map -> map.containsKey(targetGroup) }
            ?.get(targetGroup)
            .orEmpty()
    }


    fun List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>.extractGroups(): List<MuscleGroupModel> {
        return flatMap { it.keys }
    }

    fun setSelectedGroup(group: MuscleGroupModel) {
        val list: List<MuscleSubGroupModel> = _groupsAndSubgroupsWithRelations.value.firstOrNull{
            map -> map.containsKey(group)
        }?.get(group).orEmpty()
        _selectedGroup.value = group
        _subgroupsSelected.value = list
    }





    fun getGroupsWithRelations() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val groupsWithRelation = muscleGroupUseCase.getMuscleGroupsWithRelations()
                _muscleGroupsWithRelation.value = groupsWithRelation
                delay(5000)
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun getGroupsAndSubGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val result: MutableList<Map<MuscleGroupModel, List<MuscleSubGroupModel>>> = mutableListOf()

                muscleGroups.value.forEach { muscleGroup ->
                    val subgroups = getSubgroupsByGroupId(muscleGroup.muscleGroupId)
                    result.add(
                        mutableMapOf(
                            Pair(
                                first = muscleGroup,
                                second = subgroups
                            )
                        )
                    )
                }

                _groupsAndSubgroupsWithRelations.value = result
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    suspend fun getSubgroupsByGroupId(id: Int): List<MuscleSubGroupModel> {
        return try {
            val ids = muscleSubGroupUseCase.getSubGroupIdFromRelation(id)
            ids.map { subGroupId ->
                muscleSubGroupUseCase.getSubgroupById(subGroupId)
            }
        } catch (exception: Exception) {
            setErrorState(exception.message.toString())
            emptyList()
        }
    }


    fun fetchMuscleGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val muscleGroups = muscleGroupUseCase.getMuscleGroups()
                _muscleGroups.value = muscleGroups
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun fetchMuscleSubGroups() {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val muscleSubGroups = muscleGroupUseCase.getMuscleSubGroups()
                _muscleSubGroups.value = muscleSubGroups
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun fetchWorkouts(trainings: List<TrainingModel>) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                val workouts = trainings.map { training ->
                    val subGroups =
                        muscleGroupUseCase.getMuscleSubGroupsByTrainingId(training.trainingId)
                    training to subGroups
                }
                _workouts.value = workouts
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }


    fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroups: MutableList<MuscleGroupMuscleSubGroupModel>
    ) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupMuscleSubGroups.forEach {
                    muscleGroupUseCase.insertMuscleGroupMuscleSubGroup(it)
                }
                // Configuro o valor inicial
                _objSelected.value = Pair(0, false)
                setSuccessState()
                getGroupsWithRelations()
                getGroupsAndSubGroups()
                clearSubGroups()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun createInitialDatabase(isFirstInstall: Boolean) {
        if (isFirstInstall) {
            viewModelScope.launch(dispatchers.IO) {
                setLoadingState()
                try {
                    insertMuscleSubGroups()
                    _viewState.value = MuscleGroupViewState.DatabaseCreated
                } catch (exception: Exception) {
                    setErrorState(exception.message.toString())
                }
            }
        } else {
            fetchMuscleGroups()
            fetchMuscleSubGroups()
            getGroupsWithRelations()
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

    fun insertMuscleSubGroup(muscleSubGroup: MuscleSubGroupModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.insertMuscleSubGroup(muscleSubGroup)
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun insertMuscleGroup(name: String, image: BodyPart) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.insertMuscleGroup(name, image)
                fetchMuscleGroups()
                fetchMuscleSubGroups()
                clearSubGroups()
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun clearSubGroups() {
        val subGroupsSelected: List<MuscleSubGroupModel> =
            _muscleSubGroups.value.filter { it.selected }
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.clearSelectedMuscleSubGroups(subGroupsSelected)
                fetchMuscleSubGroups()
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun updateSubGroup(subGroup: MuscleSubGroupModel) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                muscleGroupUseCase.updateSubGroup(subGroup)
                fetchMuscleSubGroups()
                setSuccessState()
            } catch (exception: Exception) {
                setErrorState(exception.message.toString())
            }
        }
    }

    fun setMuscleGroupSelected(objSelected: Pair<Int, Boolean>) {
        _objSelected.value = objSelected
    }

    private fun setSuccessState() {
        Log.e(SUCCESS, SUCCESS)
        _viewState.value = MuscleGroupViewState.Success
    }

    private fun setLoadingState() {
        Log.e(LOADING, LOADING)
        _viewState.value = MuscleGroupViewState.Loading
    }

    private fun setErrorState(exception: String) {
        Log.e(EXCEPTION, exception)
        _viewState.value = MuscleGroupViewState.Error
    }

    private fun getDefaultGroup(): MuscleGroupModel =
        MuscleGroupModel(
            muscleGroupId = 0,
            name = "",
            BodyPart.LEG
        )

    companion object {
        const val EXCEPTION = "Exception"
        const val SUCCESS = "Success"
        const val LOADING = "Loading"
    }
}


