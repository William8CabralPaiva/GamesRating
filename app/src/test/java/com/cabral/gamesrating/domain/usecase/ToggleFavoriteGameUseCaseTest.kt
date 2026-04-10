package com.cabral.gamesrating.domain.usecase

import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteGameUseCaseTest {

    private lateinit var useCase: ToggleFavoriteGameUseCase
    private val repository: GamesRepository = mockk()

    @Before
    fun setup() {
        useCase = ToggleFavoriteGameUseCase(repository)
    }

    @Test
    fun invoke_shouldSaveFavoriteGame_whenAddGameIsTrue() = runTest {
        // Given
        val game = mockk<GameUi>(relaxed = true)
        val addGame = true
        coEvery { repository.saveFavoriteGame(game) } returns Unit

        // When
        useCase(addGame, game)

        // Then
        coVerify { repository.saveFavoriteGame(game) }
        coVerify(exactly = 0) { repository.deleteFavoriteGame(any()) }
    }

    @Test
    fun invoke_shouldDeleteFavoriteGame_whenAddGameIsFalse() = runTest {
        // Given
        val gameId = 10
        val game = mockk<GameUi>(relaxed = true) {
            every { id } returns gameId
        }
        val addGame = false
        coEvery { repository.deleteFavoriteGame(gameId) } returns Unit

        // When
        useCase(addGame, game)

        // Then
        coVerify { repository.deleteFavoriteGame(gameId) }
        coVerify(exactly = 0) { repository.saveFavoriteGame(any()) }
    }
}