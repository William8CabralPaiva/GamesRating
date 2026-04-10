package com.cabral.gamesrating.ui.gamedetail

import androidx.lifecycle.SavedStateHandle
import com.cabral.gamesrating.domain.usecase.GetGameDetailByIdUseCase
import com.cabral.gamesrating.ui.model.GameDetailScreenshots
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameDetailViewModelTest {

    private val getGameDetailByIdUseCase: GetGameDetailByIdUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GameDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchGames should update uiState to Success when use case returns data`() = runTest {
        // Given
        val gameId = 1
        val mockGameDetail = mockk<GameDetailScreenshots>()
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))

        every { getGameDetailByIdUseCase(gameId) } returns flowOf(mockGameDetail)

        // When
        viewModel = GameDetailViewModel(savedStateHandle, getGameDetailByIdUseCase)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is GamesUiState.Success)
        assertEquals(mockGameDetail, (currentState as GamesUiState.Success).game)
    }

    @Test
    fun `fetchGames should update uiState to Error when use case throws exception`() = runTest {
        // Given
        val gameId = 1
        val errorMessage = "Erro desconhecido"
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))

        every { getGameDetailByIdUseCase(gameId) } returns flow {
            throw Exception(errorMessage)
        }

        // When
        viewModel = GameDetailViewModel(savedStateHandle, getGameDetailByIdUseCase)
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is GamesUiState.Error)
        assertEquals(errorMessage, (currentState as GamesUiState.Error).message)
    }

    @Test
    fun `init should set initial state to Loading`() = runTest {
        // Given
        val gameId = 1
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))
        every { getGameDetailByIdUseCase(gameId) } returns flowOf(mockk())

        // When
        viewModel = GameDetailViewModel(savedStateHandle, getGameDetailByIdUseCase)

        // Then
        assertEquals(GamesUiState.Loading, viewModel.uiState.value)
    }
}