package com.cabral.gamesrating.di

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.cabral.gamesrating.data.local.GameDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabaseModuleTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `provideGameDao should return gameDao from database`() {
        // GIVEN
        val database = mockk<AppDatabase>()
        val expectedDao = mockk<GameDao>()
        every { database.gameDao() } returns expectedDao

        // WHEN
        val result = DatabaseModule.provideGameDao(database)

        // THEN
        assertEquals(expectedDao, result)
        verify(exactly = 1) { database.gameDao() }
    }

    @Test
    fun `provideAppDatabase should return a built Room database`() {
        // GIVEN
        // Usamos o ApplicationProvider do Robolectric para fornecer um contexto real
        val context = ApplicationProvider.getApplicationContext<Context>()

        // WHEN
        val result = DatabaseModule.provideAppDatabase(context)

        // THEN
        // Verificamos se o banco foi criado com sucesso
        assertNotNull(result)
        // O Room gera uma implementação da classe abstrata AppDatabase, então o check 'is' deve passar
        assert(result is AppDatabase)
        
        // Limpeza opcional se necessário
        result.close()
    }
}
