package com.cabral.gamesrating.domain.usecase

import com.cabral.gamesrating.R
import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateFavoritesOrderUseCaseTest {

    private lateinit var useCase: UpdateFavoritesOrderUseCase
    private val repository: GamesRepository = mockk()

    @Before
    fun setup() {
        useCase = UpdateFavoritesOrderUseCase(repository)
    }

    @Test
    fun `invoke should call repository updateFavoritesOrder`() = runTest {
        // Given
        val games = listOf(
            GameUi(1, "Game 1", "2023-01-01", null, 4.5, listOf(R.string.genre_action), true, 1),
            GameUi(2, "Game 2", "2023-02-01", null, 4.0, listOf(R.string.genre_rpg), true, 2)
        )
        coEvery { repository.updateFavoritesOrder(games) } returns Unit

        // When
        useCase(games)

        // Then
        coVerify(exactly = 1) { repository.updateFavoritesOrder(games) }
    }
}
