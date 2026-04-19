package com.cabral.meusjogosfavoritos.ui.gamedetail

import androidx.lifecycle.SavedStateHandle
import com.cabral.meusjogosfavoritos.domain.usecase.GetGameDetailByIdUseCase
import com.cabral.meusjogosfavoritos.domain.usecase.SaveImageUseCase
import com.cabral.meusjogosfavoritos.ui.model.GameDetailScreenshots
import com.cabral.meusjogosfavoritos.util.SettingsManager
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
    private val saveImageUseCase: SaveImageUseCase = mockk(relaxed = true)
    private val settingsManager: SettingsManager = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GameDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Default behavior for language
        every { settingsManager.getLanguage() } returns "pt"
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchGames should update uiState to Success when use case returns data`() = runTest {
        // GIVEN
        val gameId = 1
        val lang = "pt"
        val mockGameDetail = mockk<GameDetailScreenshots>()
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))

        every { settingsManager.getLanguage() } returns lang
        every { getGameDetailByIdUseCase(gameId, lang) } returns flowOf(mockGameDetail)

        // WHEN
        viewModel = GameDetailViewModel(
            savedStateHandle,
            getGameDetailByIdUseCase,
            saveImageUseCase,
            settingsManager
        )
        advanceUntilIdle()

        // THEN
        val currentState = viewModel.uiState.value
        assert(currentState is GamesUiState.Success)
        assertEquals(mockGameDetail, (currentState as GamesUiState.Success).game)
    }

    @Test
    fun `fetchGames should update uiState to Error when use case throws exception`() = runTest {
        // GIVEN
        val gameId = 1
        val lang = "pt"
        val errorMessage = "Erro desconhecido"
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))

        every { settingsManager.getLanguage() } returns lang
        every { getGameDetailByIdUseCase(gameId, lang) } returns flow {
            throw Exception(errorMessage)
        }

        // WHEN
        viewModel = GameDetailViewModel(
            savedStateHandle,
            getGameDetailByIdUseCase,
            saveImageUseCase,
            settingsManager
        )
        advanceUntilIdle()

        // THEN
        val currentState = viewModel.uiState.value
        assert(currentState is GamesUiState.Error)
        assertEquals(errorMessage, (currentState as GamesUiState.Error).message)
    }

    @Test
    fun `init should set initial state to Loading`() = runTest {
        // GIVEN
        val gameId = 1
        val lang = "pt"
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))
        
        every { settingsManager.getLanguage() } returns lang
        every { getGameDetailByIdUseCase(gameId, lang) } returns flowOf(mockk())

        // WHEN
        viewModel = GameDetailViewModel(
            savedStateHandle,
            getGameDetailByIdUseCase,
            saveImageUseCase,
            settingsManager
        )

        // THEN
        assertEquals(GamesUiState.Loading, viewModel.uiState.value)
    }
}
