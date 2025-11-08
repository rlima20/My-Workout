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
import com.example.myworkout.extensions.sortedByDayOfWeek
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    private val _muscleGroups = MutableStateFlow<List<MuscleGroupModel>>(emptyList())
    val muscleGroups: StateFlow<List<MuscleGroupModel>> get() = _muscleGroups

    private val _muscleGroupsWithRelation = MutableStateFlow<List<MuscleGroupModel>>(emptyList())
    val muscleGroupsWithRelation: StateFlow<List<MuscleGroupModel>> get() = _muscleGroupsWithRelation

    private val _muscleSubGroups = MutableStateFlow<List<MuscleSubGroupModel>>(emptyList())
    val muscleSubGroups: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroups

    private val _muscleSubGroupsByTraining = MutableStateFlow<List<MuscleSubGroupModel>>(emptyList())
    val muscleSubGroupsByTraining: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroupsByTraining

    private val _groupsAndSubgroupsWithRelations =
        MutableStateFlow<List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>>(emptyList())
    val groupsAndSubgroupsWithRelations: StateFlow<List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>>> get() = _groupsAndSubgroupsWithRelations

    private val _objSelected = MutableStateFlow(Pair(0, false))
    val objSelected: StateFlow<Pair<Int, Boolean>> get() = _objSelected

    private val _workouts =
        MutableStateFlow<List<Pair<TrainingModel, List<MuscleSubGroupModel>>>>(emptyList())
    val workouts: StateFlow<List<Pair<TrainingModel, List<MuscleSubGroupModel>>>> = _workouts

    private val _subgroupsSelected = MutableStateFlow<List<MuscleSubGroupModel>>(emptyList())
    val subgroupsSelected: StateFlow<List<MuscleSubGroupModel>> get() = _subgroupsSelected

    private val _selectedGroup = MutableStateFlow(getDefaultGroup())
    val selectedGroup: StateFlow<MuscleGroupModel> = _selectedGroup

    // ===============================================================
    // PUBLIC FUNCTIONS (API MANTIDA PARA COMPATIBILIDADE)
    // ===============================================================

    fun setSelectedGroup(group: MuscleGroupModel) {
        val list = _groupsAndSubgroupsWithRelations.value
            .firstOrNull { it.containsKey(group) }
            ?.get(group)
            .orEmpty()
        _selectedGroup.value = group
        _subgroupsSelected.value = list
    }

    fun getGroupsWithRelations() = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            _muscleGroupsWithRelation.value = muscleGroupUseCase.getMuscleGroupsWithRelations()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun getGroupsAndSubGroups() = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            val result = _muscleGroups.value.map { group ->
                val subgroups = getSubgroupsByGroupIdInternal(group.muscleGroupId)
                mapOf(group to subgroups)
            }
            _groupsAndSubgroupsWithRelations.value = result
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun fetchMuscleGroups() = viewModelScope.launch(dispatchers.IO) {
        fetchMuscleGroupsInternal()
    }

    fun fetchMuscleSubGroups() = viewModelScope.launch(dispatchers.IO) {
        fetchMuscleSubGroupsInternal()
    }

    fun fetchWorkouts(trainings: List<TrainingModel>) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            val workouts = trainings
                .sortedByDayOfWeek()
                .map { training ->
                    val subGroups =
                        muscleGroupUseCase.getMuscleSubGroupsByTrainingId(training.trainingId)
                    training to subGroups
                }
            _workouts.value = workouts
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroups: List<MuscleGroupMuscleSubGroupModel>
    ) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            if (muscleGroupMuscleSubGroups.isEmpty()) {
                setErrorState("Nenhum relacionamento informado.")
                return@launch
            }

            val muscleGroupId = muscleGroupMuscleSubGroups.first().muscleGroupId
            muscleGroupUseCase.replaceRelationsForGroup(muscleGroupId, muscleGroupMuscleSubGroups)

            _objSelected.value = Pair(0, false)

            val jobs = listOf(
                async { getGroupsWithRelationsInternal() },
                async { getGroupsAndSubGroupsInternal() },
                async { clearSubGroupsInternal() }
            )
            jobs.awaitAll()

            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun createInitialDatabase(isFirstInstall: Boolean) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                if (isFirstInstall) {
                    insertMuscleSubGroupsInternal()
                    _viewState.value = MuscleGroupViewState.DatabaseCreated
                } else {
                    val jobs = listOf(
                        async { fetchMuscleGroupsInternal() },
                        async { fetchMuscleSubGroupsInternal() },
                        async { getGroupsWithRelationsInternal() }
                    )
                    jobs.awaitAll()
                    setSuccessState()
                }
            } catch (e: Exception) {
                setErrorState(e.message.toString())
            }
        }
    }

    fun insertMuscleGroup(name: String, image: BodyPart) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            muscleGroupUseCase.insertMuscleGroup(name, image)
            val jobs = listOf(
                async { fetchMuscleGroupsInternal() },
                async { fetchMuscleSubGroupsInternal() },
                async { clearSubGroupsInternal() }
            )
            jobs.awaitAll()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun deleteGroup(group: MuscleGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            muscleGroupUseCase.deleteGroupCascade(group)
            val jobs = listOf(
                async { fetchMuscleGroupsInternal() },
                async { getGroupsWithRelationsInternal() },
                async { clearSubGroupsInternal() }
            )
            jobs.awaitAll()
            setSuccessDeleteGroup()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun clearSubGroups() = viewModelScope.launch(dispatchers.IO) {
        clearSubGroupsInternal()
    }

    fun updateSubGroup(subGroup: MuscleSubGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            muscleGroupUseCase.updateSubGroup(subGroup)
            fetchMuscleSubGroupsInternal()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun updateGroup(group: MuscleGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            muscleGroupUseCase.updateGroup(group)
            val jobs = listOf(
                async { getGroupsWithRelationsInternal() },
                async { fetchMuscleGroupsInternal() }
            )
            jobs.awaitAll()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun setMuscleGroupSelected(objSelected: Pair<Int, Boolean>) {
        _objSelected.value = objSelected
    }

    // ===============================================================
    // INTERNAL (SUSPEND) FUNCTIONS
    // ===============================================================

    private suspend fun fetchMuscleGroupsInternal() {
        setLoadingState()
        val muscleGroups = muscleGroupUseCase.getMuscleGroups()
        _muscleGroups.value = muscleGroups
        setSuccessState()
    }

    private suspend fun fetchMuscleSubGroupsInternal() {
        setLoadingState()
        val muscleSubGroups = muscleGroupUseCase.getMuscleSubGroups()
        _muscleSubGroups.value = muscleSubGroups
        setSuccessState()
    }

    private suspend fun getGroupsWithRelationsInternal() {
        _muscleGroupsWithRelation.value = muscleGroupUseCase.getMuscleGroupsWithRelations()
    }

    private suspend fun getGroupsAndSubGroupsInternal() {
        val result = _muscleGroups.value.map { group ->
            val subgroups = getSubgroupsByGroupIdInternal(group.muscleGroupId)
            mapOf(group to subgroups)
        }
        _groupsAndSubgroupsWithRelations.value = result
    }

    private suspend fun getSubgroupsByGroupIdInternal(id: Int): List<MuscleSubGroupModel> =
        try {
            val ids = muscleSubGroupUseCase.getSubGroupIdFromRelation(id)
            ids.map { subId -> muscleSubGroupUseCase.getSubgroupById(subId) }
        } catch (e: Exception) {
            setErrorState(e.message.toString())
            emptyList()
        }

    private suspend fun insertMuscleSubGroupsInternal() {
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, name ->
            muscleGroupUseCase.insertMuscleSubGroup(
                MuscleSubGroupModel(id = index + 1, name = name)
            )
        }
    }

    private suspend fun clearSubGroupsInternal() {
        setLoadingState()
        val selected = _muscleSubGroups.value.filter { it.selected }
        muscleGroupUseCase.clearSelectedMuscleSubGroups(selected)
        fetchMuscleSubGroupsInternal()
        setSuccessState()
    }

    // ===============================================================
    // STATE HELPERS
    // ===============================================================

    private fun setSuccessState() {
        Log.e(SUCCESS, SUCCESS)
        _viewState.value = MuscleGroupViewState.Success
    }

    private fun setSuccessDeleteGroup() {
        Log.e(SUCCESS, SUCCESS)
        _viewState.value = MuscleGroupViewState.SuccessDeleteGroup
    }

    private fun setLoadingState() {
        Log.e(LOADING, LOADING)
        _viewState.value = MuscleGroupViewState.Loading
    }

    private fun setErrorState(exception: String) {
        Log.e(EXCEPTION, exception)
        _viewState.value = MuscleGroupViewState.Error
    }

    private fun getDefaultGroup() = MuscleGroupModel(0, "", BodyPart.LEG)

    companion object {
        const val EXCEPTION = "Exception"
        const val SUCCESS = "Success"
        const val LOADING = "Loading"
    }
}
