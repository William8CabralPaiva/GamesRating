package com.cabral.gamesrating.domain.usecase

import androidx.paging.PagingData
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.domain.repository.GamesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllGamesUseCaseTest {

    private lateinit var useCase: GetAllGamesUseCase
    private val repository: GamesRepository = mockk()

    @Before
    fun setup() {
        useCase = GetAllGamesUseCase(repository)
    }

    @Test
    fun invoke_shouldReturnPagingDataFlowFromRepository() = runTest {
        // Given
        val search = "Cyberpunk"
        val expectedPagingData = flowOf(PagingData.from(listOf(mockk<Game>())))
        every { repository.getAllGames(search) } returns expectedPagingData

        // When
        val result = useCase(search).first()

        // Then
        assertEquals(expectedPagingData.first(), result)
        verify { repository.getAllGames(search) }
    }
}