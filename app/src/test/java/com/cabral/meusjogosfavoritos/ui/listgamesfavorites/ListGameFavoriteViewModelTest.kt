package com.cabral.meusjogosfavoritos.ui.listgamesfavorites

import com.cabral.meusjogosfavoritos.domain.usecase.DeleteFavoriteGameUseCase
import com.cabral.meusjogosfavoritos.domain.usecase.GetAllFavoritesUseCase
import com.cabral.meusjogosfavoritos.domain.usecase.UpdateFavoritesOrderUseCase
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import io.mockk.coEvery
import io.mockk.coVerify
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
class ListGameFavoriteViewModelTest {

    private val getAllFavoritesUseCase: GetAllFavoritesUseCase = mockk()
    private val deleteFavoriteGameUseCase: DeleteFavoriteGameUseCase = mockk()
    private val updateFavoritesOrderUseCase: UpdateFavoritesOrderUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ListGameFavoriteViewModel

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
        val mockGames = listOf(mockk<GameUi>(), mockk<GameUi>())
        every { getAllFavoritesUseCase() } returns flowOf(mockGames)

        // When
        viewModel = ListGameFavoriteViewModel(
            getAllFavoritesUseCase,
            deleteFavoriteGameUseCase,
            updateFavoritesOrderUseCase
        )
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is FavoritesGamesUiState.Success)
        assertEquals(mockGames, (currentState as FavoritesGamesUiState.Success).listGames)
    }

    @Test
    fun `fetchGames should update uiState to Error when use case fails`() = runTest {
        // Given
        val errorMessage = "Erro ao buscar favoritos"
        every { getAllFavoritesUseCase() } returns flow {
            throw Exception(errorMessage)
        }

        // When
        viewModel = ListGameFavoriteViewModel(
            getAllFavoritesUseCase,
            deleteFavoriteGameUseCase,
            updateFavoritesOrderUseCase
        )
        advanceUntilIdle()

        // Then
        val currentState = viewModel.uiState.value
        assert(currentState is FavoritesGamesUiState.Error)
        assertEquals(errorMessage, (currentState as FavoritesGamesUiState.Error).message)
    }

    @Test
    fun `removeFavorite should call deleteFavoriteGameUseCase`() = runTest {
        // Given
        val gameId = 123
        every { getAllFavoritesUseCase() } returns flowOf(emptyList())
        coEvery { deleteFavoriteGameUseCase(gameId) } returns Unit
        viewModel = ListGameFavoriteViewModel(
            getAllFavoritesUseCase,
            deleteFavoriteGameUseCase,
            updateFavoritesOrderUseCase
        )

        // When
        viewModel.removeFavorite(gameId)
        advanceUntilIdle()

        // Then
        coVerify { deleteFavoriteGameUseCase(gameId) }
    }

    @Test
    fun `init should start with Loading state`() = runTest {
        // Given
        every { getAllFavoritesUseCase() } returns flowOf(emptyList())

        // When
        viewModel = ListGameFavoriteViewModel(
            getAllFavoritesUseCase,
            deleteFavoriteGameUseCase,
            updateFavoritesOrderUseCase
        )

        // Then
        assertEquals(FavoritesGamesUiState.Loading, viewModel.uiState.value)
    }
}