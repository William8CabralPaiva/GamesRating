package com.cabral.meusjogosfavoritos.ui.listgames

import androidx.paging.PagingData
import com.cabral.meusjogosfavoritos.data.model.Game
import com.cabral.meusjogosfavoritos.domain.usecase.GetAllFavoritesUseCase
import com.cabral.meusjogosfavoritos.domain.usecase.GetAllGamesUseCase
import com.cabral.meusjogosfavoritos.domain.usecase.ToggleFavoriteGameUseCase
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListGameViewModelTest {

    private val getAllGamesUseCase: GetAllGamesUseCase = mockk()
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase = mockk()
    private val toggleFavoriteGameUseCase: ToggleFavoriteGameUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ListGameViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Default mock for init block
        every { getAllFavoritesUseCase() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should fetch favorites and update favoriteIds`() = runTest {
        // Given
        val favoriteGames = listOf(mockk<GameUi> { every { id } returns 10 })
        every { getAllFavoritesUseCase() } returns flowOf(favoriteGames)

        // When
        viewModel = ListGameViewModel(getAllGamesUseCase, getAllFavoritesUseCase, toggleFavoriteGameUseCase)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.favoriteIds.value.contains(10))
    }

    @Test
    fun `updateSearch should update searchQuery state`() = runTest {
        // Given
        viewModel = ListGameViewModel(getAllGamesUseCase, getAllFavoritesUseCase, toggleFavoriteGameUseCase)
        val query = "Batman"

        // When
        viewModel.updateSearch(query)

        // Then
        assertEquals(query, viewModel.searchQuery.value)
    }

    @Test
    fun `games flow should call getAllGamesUseCase when query changes after debounce`() = runTest {
        // Given
        val query = "Zelda"
        val pagingData = PagingData.empty<Game>()
        every { getAllGamesUseCase(query) } returns flowOf(pagingData)
        viewModel = ListGameViewModel(getAllGamesUseCase, getAllFavoritesUseCase, toggleFavoriteGameUseCase)

        // When
        viewModel.updateSearch(query)
        advanceTimeBy(501) // Pass debounce time

        val result = viewModel.games.first()

        // Then
        coVerify { getAllGamesUseCase(query) }
        assertTrue(result is PagingData<GameUi>)
    }

    @Test
    fun `toggleFavorite should call toggleFavoriteGameUseCase`() = runTest {
        // Given
        val gameUi = mockk<GameUi>()
        val isFavorite = true
        coEvery { toggleFavoriteGameUseCase(isFavorite, gameUi) } returns Unit
        viewModel = ListGameViewModel(getAllGamesUseCase, getAllFavoritesUseCase, toggleFavoriteGameUseCase)

        // When
        viewModel.toggleFavorite(isFavorite, gameUi)
        advanceUntilIdle()

        // Then
        coVerify { toggleFavoriteGameUseCase(isFavorite, gameUi) }
    }
}