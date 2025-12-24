package com.example.myworkout.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.MUSCLE_SUB_GROUP_NAMES
import com.example.myworkout.domain.mapper.toGroupSubGroupModel
import com.example.myworkout.domain.model.GroupSubGroupModel
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.model.MuscleGroupMuscleSubGroupModel
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.enums.Sort
import com.example.myworkout.extensions.sortedByDayOfWeek
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class MuscleGroupViewModel(
    private val useCase: MuscleGroupUseCase,
    private val dispatchers: Dispatchers
) : ViewModel() {

    /**
     * Essa variável é utilizada para compor os workouts para a HomeScreen
     */
    private val _subGroups = MutableStateFlow<List<SubGroupModel>>(emptyList())
    val subGroups: StateFlow<List<SubGroupModel>> get() = _subGroups

    private val _viewState: MutableStateFlow<MuscleGroupViewState> =
        MutableStateFlow(MuscleGroupViewState.Loading)
    val viewState: StateFlow<MuscleGroupViewState> get() = _viewState

    private val _muscleGroups = MutableStateFlow<List<MuscleGroupModel>>(emptyList())
    val muscleGroups: StateFlow<List<MuscleGroupModel>> get() = _muscleGroups

    private val _muscleGroupsWithRelation = MutableStateFlow<List<MuscleGroupModel>>(emptyList())
    val muscleGroupsWithRelation: StateFlow<List<MuscleGroupModel>> get() = _muscleGroupsWithRelation

    private val _muscleSubGroups = MutableStateFlow<List<MuscleSubGroupModel>>(emptyList())
    val muscleSubGroups: StateFlow<List<MuscleSubGroupModel>> get() = _muscleSubGroups

    private var groupsAndSubgroupsWithRelations: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>> =
        emptyList()

    private val _objSelected = MutableStateFlow(Pair(0, false))
    val objSelected: StateFlow<Pair<Int, Boolean>> get() = _objSelected

    private val _newWorkouts =
        MutableStateFlow<List<Pair<TrainingModel, List<SubGroupModel>>>>(emptyList())
    val newWorkouts: StateFlow<List<Pair<TrainingModel, List<SubGroupModel>>>> = _newWorkouts

    private val _subgroupsSelected = MutableStateFlow<List<MuscleSubGroupModel>>(emptyList())
    val subgroupsSelected: StateFlow<List<MuscleSubGroupModel>> get() = _subgroupsSelected

    private val _selectedGroup = MutableStateFlow(getDefaultGroup())
    val selectedGroup: StateFlow<MuscleGroupModel> = _selectedGroup

    private val _selectedSort: MutableStateFlow<String> = MutableStateFlow(Sort().sortAZ)
    val selectedSort: StateFlow<String> get() = _selectedSort

    private val _query = MutableStateFlow(Constants().emptyString())
    val query: StateFlow<String> get() = _query

    private val _noResult = MutableStateFlow(false)
    val noResult: StateFlow<Boolean> get() = _noResult

    fun setQuery(filter: String) {
        _query.value = filter
    }

    fun clearQuery() {
        _query.value = ""
    }

    fun setSelectedSort(selectedSort: String) {
        _selectedSort.value = selectedSort
    }

    fun sortAndFilterSubGroups() {
        val subgroupsSortedAndFiltered = if (_query.value.isEmpty()) setNormalSortWithoutQuery()
        else setSortWithQuery()
        _noResult.value = subgroupsSortedAndFiltered.isEmpty()
        _muscleSubGroups.value = subgroupsSortedAndFiltered
    }

    private fun setNormalSortWithoutQuery(): List<MuscleSubGroupModel> {
        return if (_selectedSort.value == Sort().sortAZ) _muscleSubGroups.value.sortedBy { it.name.lowercase() }
        else _muscleSubGroups.value.sortedByDescending { it.name.lowercase() }
    }

    private fun setSortWithQuery(): List<MuscleSubGroupModel> {
        return _muscleSubGroups.value.filter { it.name.contains(_query.value, ignoreCase = true) }
    }

    fun setSelectedGroup(group: MuscleGroupModel) {
        val list = groupsAndSubgroupsWithRelations.firstOrNull { it.containsKey(group) }?.get(group)
            .orEmpty()
        _selectedGroup.value = group
        _subgroupsSelected.value = list
    }

    fun getGroupsWithRelations() = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            _muscleGroupsWithRelation.value = useCase.getMuscleGroupsWithRelations()
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
            groupsAndSubgroupsWithRelations = result
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

    fun fetchSubGroups() = viewModelScope.launch(dispatchers.IO) {
        fetchSubGroupsInternal()
    }

    fun fetchWorkouts(trainings: List<TrainingModel>) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            val workouts = trainings
                .sortedByDayOfWeek()
                .map { training ->
                    val subGroups =
                        useCase.getSubGroupsByTrainingId(+training.trainingId)
                    training to subGroups
                }
            _newWorkouts.value = workouts
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun insertMuscleGroupMuscleSubGroup(
        muscleGroupMuscleSubGroups: List<MuscleGroupMuscleSubGroupModel>,
    ) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            val muscleGroupId = muscleGroupMuscleSubGroups.first().muscleGroupId

            if (muscleGroupMuscleSubGroups.isEmpty()) {
                setErrorState(NO_RELATION_ERROR)
                return@launch
            }

            useCase.replaceRelationsForGroup(muscleGroupId, muscleGroupMuscleSubGroups)
            val jobs = listOf(
                async {
                    replaceNewRelationsForGroup(
                        muscleGroupId,
                        muscleGroupMuscleSubGroups.toGroupSubGroupModel()
                    )
                },
                async { getGroupsWithRelationsInternal() },
                async { getGroupsAndSubGroupsInternal() },
                async { clearSubGroupsInternal() }
            )
            jobs.awaitAll()

            unselectMuscleGroup()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun insertNewSubGroup(subgroupName: String) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()

        val newId = getLastInsertedSubGroupId(muscleSubGroups.value) + 1

        val muscleSubGroup =
            MuscleSubGroupModel(
                id = newId,
                name = subgroupName
            )
        val subgroup =
            SubGroupModel(
                id = newId,
                name = subgroupName
            )

        try {
            val jobs = listOf(
                async { useCase.insertMuscleSubGroup(muscleSubGroup) },
                async { useCase.insertSubGroup(subgroup) },
                async { useCase.fetchMuscleSubGroups() },
                async { _muscleSubGroups.value = useCase.getMuscleSubGroups() },
                async { useCase.fetchSubGroups() }
            )
            jobs.awaitAll()

            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }


    private suspend fun replaceNewRelationsForGroup(
        groupId: Int,
        groupSubGroups: List<GroupSubGroupModel>
    ) {
        useCase.replaceNewRelationsForGroup(
            groupId,
            groupSubGroups
        )
    }

    fun createInitialDatabase(isFirstInstall: Boolean) {
        viewModelScope.launch(dispatchers.IO) {
            setLoadingState()
            try {
                if (isFirstInstall) {
                    insertMuscleSubGroupsInternal()
                    insertSubGroupsInternal()
                    _viewState.value = MuscleGroupViewState.DatabaseCreated
                } else {
                    val jobs = listOf(
                        async { fetchMuscleGroupsInternal() },
                        async { fetchMuscleSubGroupsInternal() },
                        async { fetchSubGroupsInternal() },
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
            useCase.insertMuscleGroup(name, image)
            val jobs = listOf(
                async { fetchMuscleGroupsInternal() },
                async { fetchMuscleSubGroupsInternal() },
                async { fetchSubGroupsInternal() },
                async { clearSubGroupsInternal() }
            )
            jobs.awaitAll()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    private fun getLastInsertedSubGroupId(subGroups: List<MuscleSubGroupModel>): Int {
        return subGroups.maxByOrNull { it.id }?.id
            ?: throw IllegalStateException("Falha ao obter o ID do treinamento recém inserido")
    }

    fun deleteGroup(group: MuscleGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            useCase.deleteGroupCascade(group)
            val jobs = listOf(
                async { fetchMuscleGroupsInternal() },
                async { getGroupsWithRelationsInternal() },
                async { clearSubGroupsInternal() }
            )
            unselectMuscleGroup()
            jobs.awaitAll()
            setSuccessDeleteGroup()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun deleteSubgroup(subgroup: MuscleSubGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            useCase.deleteSubgroup(subgroup)

            val jobs = listOf(
                async { fetchMuscleSubGroups() },
                // Todo - fetchNewSubgroups
            )
            // unselectMuscleGroup() Todo - Talvez fazer isso também
            jobs.awaitAll()
            // setSuccessDeleteGroup()
            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    private fun unselectMuscleGroup() {
        _objSelected.value = Pair(0, false)
    }

    fun clearSubGroups() = viewModelScope.launch(dispatchers.IO) {
        clearSubGroupsInternal()
    }

    fun unselectSubgroups(trainingModel: TrainingModel) {
        setLoadingState()
        try {
            viewModelScope.launch(Dispatchers.IO) {
                unselectSubgroupsInternal(trainingModel)
            }
            fetchSubGroups()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun updateSubGroup(subGroup: MuscleSubGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            useCase.updateSubGroup(subGroup)

            val jobs = listOf(
                async { fetchMuscleGroupsInternal() }
            )
            jobs.awaitAll()

            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun updateNewSubGroup(subGroup: SubGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            useCase.updateNewSubGroup(subGroup)

            val jobs = listOf(
                async { fetchMuscleGroupsInternal() }
            )
            jobs.awaitAll()

            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    fun updateGroup(group: MuscleGroupModel) = viewModelScope.launch(dispatchers.IO) {
        setLoadingState()
        try {
            useCase.updateGroup(group)
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
        val muscleGroups = useCase.getMuscleGroups()
        _muscleGroups.value = muscleGroups
        setSuccessState()
    }

    private suspend fun unselectSubgroupsInternal(training: TrainingModel) {
        useCase.unselectSubgroups(training)
        val subGroups = useCase.getSubGroups()
        _subGroups.value = subGroups
        setSuccessState()
    }

    private suspend fun fetchMuscleSubGroupsInternal() {
        setLoadingState()
        val muscleSubGroups = useCase.getMuscleSubGroups()
        _muscleSubGroups.value = muscleSubGroups
        sortAndFilterSubGroups()
        setSuccessState()
    }

    private suspend fun fetchSubGroupsInternal() {
        setLoadingState()
        val muscleSubGroups = useCase.getSubGroups()
        _subGroups.value = muscleSubGroups
        setSuccessState()
    }

    private suspend fun getGroupsWithRelationsInternal() {
        _muscleGroupsWithRelation.value = useCase.getMuscleGroupsWithRelations()
    }

    private suspend fun getGroupsAndSubGroupsInternal() {
        val result = _muscleGroups.value.map { group ->
            val subgroups = getSubgroupsByGroupIdInternal(group.muscleGroupId)
            mapOf(group to subgroups)
        }
        groupsAndSubgroupsWithRelations = result
    }

    private suspend fun getSubgroupsByGroupIdInternal(id: Int): List<MuscleSubGroupModel> =
        try {
            val ids = useCase.getSubGroupIdFromRelation(id)
            ids.map { subId -> useCase.getSubgroupById(subId) }
        } catch (e: Exception) {
            setErrorState(e.message.toString())
            emptyList()
        }

    private suspend fun insertMuscleSubGroupsInternal() {
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, name ->
            useCase.insertMuscleSubGroup(
                MuscleSubGroupModel(id = index + 1, name = name)
            )
        }
    }

    private suspend fun insertSubGroupsInternal() {
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, name ->
            useCase.insertSubGroup(
                SubGroupModel(id = index + 1, name = name)
            )
        }
    }


    private suspend fun clearSubGroupsInternal() {
        setLoadingState()
        val selected = _muscleSubGroups.value
        useCase.clearSelectedMuscleSubGroups(selected)
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

    private fun getDefaultGroup() = MuscleGroupModel(0, "")

    companion object {
        const val EXCEPTION = "Exception"
        const val SUCCESS = "Success"
        const val LOADING = "Loading"
        const val NO_RELATION_ERROR = "Nenhum relacionamento informado."
    }
}
