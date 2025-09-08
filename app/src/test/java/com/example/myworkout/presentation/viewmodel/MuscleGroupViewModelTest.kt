package com.example.myworkout.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myworkout.domain.model.MuscleGroupModel
import com.example.myworkout.domain.usecase.musclegroup.MuscleGroupUseCase
import com.example.myworkout.enums.BodyPart
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        muscleGroupUseCase = mockk(relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MuscleGroupViewModel(muscleGroupUseCase, testDispatcher)
    }

    @After
    fun tearDown() {
        TODO("Not yet implemented")
    }

//    @Test
//    fun shouldReturnSuccessStateAfterSearchingMuscleGroupsWithRelations() = runTest {
//        // Dados do teste
//        val groupsWithRelations = listOf(
//            MuscleGroupModel(1, "Peito", BodyPart.OTHER),
//            MuscleGroupModel(2, "Costas", BodyPart.OTHER)
//        )
//
//        // Simular o comportamento do usecase
//        coEvery { muscleGroupUseCase.getMuscleGroupsWithRelations() } returns groupsWithRelations
//
//        repeat(10) {
//            // Aguarda um pequeno intervalo
//            advanceTimeBy(100)
//
//            // Verifica o estado
//            if (viewModel.muscleGroupsWithRelation.value == groupsWithRelations) {
//                assertEquals(groupsWithRelations, viewModel.muscleGroupsWithRelation.value)
//                return@repeat // Saia do loop se o estado estiver correto
//            }
//        }
//
//        // Se não encontrou o resultado, falha o teste
//        assertEquals(groupsWithRelations, viewModel.muscleGroupsWithRelation.value)
//    }

    // Ação no viewModel
//        viewModel.dispatchViewAction(MuscleGroupViewAction.FetchRelations)


    //Verifica se o estado é de sucesso e se a lista está corretamente populada
//         assertEquals(groupsWithRelations, viewModel.muscleGroupsWithRelation.value)
//        assertEquals(
//            MuscleGroupViewState.SuccessGetGroupsWithRelations(
//                groupsWithRelations
//            ), viewModel.viewState.value
//        )
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