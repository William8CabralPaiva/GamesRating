package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllFavoritesUseCaseTest {

    private lateinit var useCase: GetAllFavoritesUseCase
    private val repository: GamesRepository = mockk()

    @Before
    fun setup() {
        useCase = GetAllFavoritesUseCase(repository)
    }

    @Test
    fun invoke_shouldReturnFavoritesFlowFromRepository() = runTest {
        // Given
        val expectedFavorites = listOf(mockk<GameUi>(), mockk<GameUi>())
        every { repository.getAllFavorites() } returns flowOf(expectedFavorites)

        // When
        val result = useCase().first()

        // Then
        assertEquals(expectedFavorites, result)
        verify { repository.getAllFavorites() }
    }

    @Test
    fun invoke_shouldReturnEmptyList_whenRepositoryHasNoFavorites() = runTest {
        // Given
        val expectedFavorites = emptyList<GameUi>()
        every { repository.getAllFavorites() } returns flowOf(expectedFavorites)

        // When
        val result = useCase().first()

        // Then
        assertEquals(expectedFavorites, result)
        verify { repository.getAllFavorites() }
    }
}