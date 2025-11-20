package com.example.myworkout.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.myworkout.extensions.sortedByDayOfWeek
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class TrainingSubGroupState(
    val subGroup: SubGroupModel,
    val isSelected: Boolean = false
)

open class MuscleGroupViewModel(
    private val muscleGroupUseCase: MuscleGroupUseCase,
    private val muscleSubGroupUseCase: MuscleGroupUseCase,
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

    private var groupsAndSubgroupsWithRelations: List<Map<MuscleGroupModel, List<MuscleSubGroupModel>>> = emptyList()

    private val _objSelected = MutableStateFlow(Pair(0, false))
    val objSelected: StateFlow<Pair<Int, Boolean>> get() = _objSelected

//    private val _newWorkouts = MutableStateFlow<List<Pair<TrainingModel, List<SubGroupModel>>>>(emptyList())
//    val newWorkouts: StateFlow<List<Pair<TrainingModel, List<SubGroupModel>>>> = _newWorkouts

    private val _newWorkouts =
        MutableStateFlow<List<Pair<TrainingModel, List<TrainingSubGroupState>>>>(emptyList())
    val newWorkouts = _newWorkouts

    private val _subgroupsSelected = MutableStateFlow<List<MuscleSubGroupModel>>(emptyList())
    val subgroupsSelected: StateFlow<List<MuscleSubGroupModel>> get() = _subgroupsSelected

    private val _selectedGroup = MutableStateFlow(getDefaultGroup())
    val selectedGroup: StateFlow<MuscleGroupModel> = _selectedGroup

    private val _trainingSubGroups = MutableStateFlow<Map<Int, List<TrainingSubGroupState>>>(emptyMap())
    val trainingSubGroups = _trainingSubGroups

    private val _trainingSubGroupsState =
        MutableStateFlow<Map<Int, List<TrainingSubGroupState>>>(emptyMap())

    val trainingSubGroupsState = _trainingSubGroupsState

//    fun loadSubGroupsForTraining(trainingId: Int, subGroups: List<MuscleSubGroupModel>) {
//        val state = subGroups.map { subgroup ->
//            TrainingSubGroupState(subGroup = subgroup.copy()) // 🔥 cópia!
//        }
//
//        _trainingSubGroups.update { current ->
//            current + (trainingId to state)
//        }
//    }
//
//    fun toggleSubGroupSelection(trainingId: Int, subGroupId: Int) {
//        _trainingSubGroups.update { map ->
//            val updatedList = map[trainingId]?.map { item ->
//                if (item.subGroup.id == subGroupId)
//                    item.copy(isSelected = !item.isSelected)
//                else item
//            } ?: emptyList()
//
//            map + (trainingId to updatedList)
//        }
//    }

    fun setSelectedGroup(group: MuscleGroupModel) {
        val list = groupsAndSubgroupsWithRelations.firstOrNull { it.containsKey(group) }?.get(group).orEmpty()
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
            val workouts: List<Pair<TrainingModel, List<TrainingSubGroupState>>> =
                trainings
                    .sortedByDayOfWeek()
                    .map { training ->

                        val baseSubGroups =
                            muscleGroupUseCase.getSubGroupsByTrainingId(training.trainingId)

                        // Pega o estado atual armazenado no mapa
                        val selectedMap =
                             trainingSubGroupsState.value[training.trainingId] ?: emptyList()

                        // Mescla estado vindo do banco + estado de seleção
                        val merged = baseSubGroups.map { sg ->
                            val selected = selectedMap.firstOrNull { it.subGroup.id == sg.id }?.isSelected ?: false

                            TrainingSubGroupState(
                                subGroup = sg,
                                isSelected = selected
                            )
                        }

                        training to merged
                    }

            _newWorkouts.value = workouts
            setSuccessState()

        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

//    fun fetchWorkouts(trainings: List<TrainingModel>) = viewModelScope.launch(dispatchers.IO) {
//        setLoadingState()
//        try {
//            val workouts = trainings
//                .sortedByDayOfWeek()
//                .map { training ->
//                    val subGroups = muscleGroupUseCase.getSubGroupsByTrainingId(training.trainingId)
//
//                    // 🔥 Aqui está a correção: cada treino ganha seu próprio estado
//                    val uiStates = subGroups.map { subgroup ->
//                        TrainingSubGroupState(
//                            subGroup = subgroup.copy(),   // evita compartilhamento de instância
//                            isSelected = false      // estado inicial
//                        )
//                    }
//
//                    training to uiStates
//                }
//
//            _newWorkouts.value = workouts
//            setSuccessState()
//        } catch (e: Exception) {
//            setErrorState(e.message.toString())
//        }
//    }

    fun toggleSubGroupSelection(trainingId: Int, subGroupId: Int) {
        _newWorkouts.value = _newWorkouts.value.map { (training, subGroups) ->
            if (training.trainingId == trainingId) {
                val updatedSubGroups = subGroups.map { state ->
                    if (state.subGroup.id == subGroupId) {
                        state.copy(isSelected = !state.isSelected)
                    } else state
                }
                training to updatedSubGroups
            } else {
                training to subGroups
            }
        }
    }

//    fun fetchWorkouts(trainings: List<TrainingModel>) = viewModelScope.launch(dispatchers.IO) {
//        setLoadingState()
//        try {
//            val workouts = trainings
//                .sortedByDayOfWeek()
//                .map { training ->
//                    val subGroups =
//                        muscleGroupUseCase.getSubGroupsByTrainingId(+training.trainingId)
//                    training to subGroups
//                }
//            _newWorkouts.value = workouts
//            setSuccessState()
//        } catch (e: Exception) {
//            setErrorState(e.message.toString())
//        }
//    }


//    fun fetchWorkouts(trainings: List<TrainingModel>) =
//        viewModelScope.launch(dispatchers.IO) {
//        setLoadingState()
//        try {
//            val allSubGroups = _subGroups.value // lista global (proveniente da tabela SubGroup)
//            val workouts = trainings
//                .sortedByDayOfWeek()
//                .map { training ->
//                    // 1) cria cópias "limpas" (selected = false)
//                    val freshSubGroups = allSubGroups.map { sub -> sub.copy(selected = false) }
//
//                    // 2) pega os subgroups relacionados a esse treino (pela useCase)
//                    val relatedSubGroups: List<SubGroupModel> =
//                        muscleGroupUseCase.getSubGroupsByTrainingId(training.trainingId)
//
//                    // 3) marca como selected = true apenas os que realmente pertencem a esse treino
//                    val relatedIds = relatedSubGroups.map { it.id }.toSet()
//                    val subGroupsForThisTraining = freshSubGroups.map { sub ->
//                        if (relatedIds.contains(sub.id)) sub.copy(selected = true) else sub
//                    }
//
//                    training to subGroupsForThisTraining
//                }
//
//            _newWorkouts.value = workouts
//            setSuccessState()
//        } catch (e: Exception) {
//            setErrorState(e.message.toString())
//        }
//    }

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

            muscleGroupUseCase.replaceRelationsForGroup(muscleGroupId, muscleGroupMuscleSubGroups)
            _objSelected.value = Pair(0, false)

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

            setSuccessState()
        } catch (e: Exception) {
            setErrorState(e.message.toString())
        }
    }

    private suspend fun replaceNewRelationsForGroup(
        groupId: Int,
        groupSubGroups: List<GroupSubGroupModel>
    ) {
        muscleGroupUseCase.replaceNewRelationsForGroup(
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
            muscleGroupUseCase.insertMuscleGroup(name, image)
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
            muscleGroupUseCase.updateNewSubGroup(subGroup)

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

    private suspend fun fetchSubGroupsInternal() {
        setLoadingState()
        val muscleSubGroups = muscleGroupUseCase.getSubGroups()
        _subGroups.value = muscleSubGroups
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
        groupsAndSubgroupsWithRelations = result
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

    private suspend fun insertSubGroupsInternal() {
        MUSCLE_SUB_GROUP_NAMES.forEachIndexed { index, name ->
            muscleGroupUseCase.insertSubGroup(
                SubGroupModel(id = index + 1, name = name)
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
        const val NO_RELATION_ERROR = "Nenhum relacionamento informado."
    }
}
