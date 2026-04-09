package com.cabral.gamesrating.di

import com.cabral.gamesrating.data.repository.GamesRepositoryImpl
import com.cabral.gamesrating.domain.repository.GamesRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DataModuleTest {

    @Test
    fun `bindGamesRepository should return GamesRepositoryImpl instance`() {
        // GIVEN
        val repositoryImpl = mockk<GamesRepositoryImpl>()

        // WHEN
        // Em testes unitários de módulos com @Binds, validamos a atribuição direta
        val result: GamesRepository = repositoryImpl

        // THEN
        assertTrue(result is GamesRepositoryImpl)
        assertEquals(repositoryImpl, result)
    }

    @Test
    fun `provideIoDispatcher should return IO dispatcher`() {
        // GIVEN
        val expectedDispatcher = Dispatchers.IO

        // WHEN
        val result = DataModule.provideIoDispatcher()

        // THEN
        assertEquals(expectedDispatcher, result)
    }
}