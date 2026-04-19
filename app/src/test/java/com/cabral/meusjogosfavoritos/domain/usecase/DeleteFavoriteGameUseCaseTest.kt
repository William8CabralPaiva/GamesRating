package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteFavoriteGameUseCaseTest {

    private lateinit var useCase: DeleteFavoriteGameUseCase
    private val repository: GamesRepository = mockk()

    @Before
    fun setup() {
        useCase = DeleteFavoriteGameUseCase(repository)
    }

    @Test
    fun invoke_shouldCallDeleteFavoriteGameOnRepository() = runTest {
        // Given
        val gameId = 1
        coEvery { repository.deleteFavoriteGame(gameId) } returns Unit

        // When
        useCase(gameId)

        // Then
        coVerify { repository.deleteFavoriteGame(gameId) }
    }
}