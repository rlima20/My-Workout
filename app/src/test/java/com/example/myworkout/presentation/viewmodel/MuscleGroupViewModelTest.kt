package com.example.myworkout.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import com.example.myworkout.presentation.viewmodel.viewaction.MuscleGroupViewAction
import com.example.myworkout.presentation.viewmodel.viewstate.MuscleGroupViewState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val testDispatcher = Dispatchers
}

@OptIn(ExperimentalCoroutinesApi::class)
class MuscleGroupViewModelTest : BaseTest() {
    @MockK
    private lateinit var muscleGroupUseCase: MuscleGroupUseCase
    private lateinit var viewModel: MuscleGroupViewModel

    @Before
    fun setUp() {
        muscleGroupUseCase = mockk()
        MockitoAnnotations.initMocks(this)
        viewModel = MuscleGroupViewModel(muscleGroupUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        TODO("Not yet implemented")
    }

    @Test
    fun shouldReturnSuccessStateAfterSearchingMuscleGroupsWithRelations() = runTest {
        // Dados do teste
        val groupsWithRelations = listOf(
            MuscleGroupModel(1, "Peito", BodyPart.OTHER),
            MuscleGroupModel(2, "Costas", BodyPart.OTHER)
        )

        // Simular o comportamento do usecase
        muscleGroupUseCase = mockk()
        coEvery { muscleGroupUseCase.getMuscleGroupsWithRelations() }.returns(groupsWithRelations)

        // Ação no viewModel
        viewModel.dispatchViewAction(MuscleGroupViewAction.FetchRelations)

        //Verifica se o estado é de sucesso e se a lista está corretamente populada
         assertEquals(groupsWithRelations, viewModel.muscleGroupsWithRelation.value)
//        assertEquals(
//            MuscleGroupViewState.SuccessGetGroupsWithRelations(
//                groupsWithRelations
//            ), viewModel.viewState.value
//        )
    }
}

//private fun getGroupsWithRelations() {
//    viewModelScope.launch(dispatchers.IO) {
//        setLoadingState()
//        try {
//            val groupsWithRelation = muscleGroupUseCase.getMuscleGroupsWithRelations()
//            _muscleGroupsWithRelation.value = groupsWithRelation
//            setSuccessState(
//                MuscleGroupViewState.SuccessGetGroupsWithRelations(
//                    _muscleGroupsWithRelation.value
//                )
//            )
//        } catch (exception: Exception) {
//            setErrorState(exception.message.toString())
//        }
//    }
//}