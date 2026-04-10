package com.cabral.gamesrating.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.cabral.gamesrating.data.local.GameDao
import com.cabral.gamesrating.data.local.GameFavoriteEntity
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: GameDao

    @Before
    fun setup() {
        // Given
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        )
            .allowMainThreadQueries() // Necessário para testes na JVM
            .build()

        dao = db.gameDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun should_Return_GameDao_Successfully() {
        // When
        val result = db.gameDao()

        // Then
        assertNotNull(result)
    }

    @Test
    fun should_Verify_Database_Is_Open_When_Accessed() {
        // Act
        // Forçamos o Room a abrir a conexão de fato através de uma operação simples
        db.openHelper.writableDatabase
        val isOpen = db.isOpen

        // Then
        assertTrue("O banco de dados deveria estar aberto após o acesso", isOpen)
    }

    @Test
    fun should_Successfully_Execute_Dao_Operation() {
        // Act
        // Teste funcional básico para garantir que a estrutura do DB (entities/converters) está correta
        val game = GameFavoriteEntity(
            id = 1,
            orderId = null,
            name = "Test Game",
            genres = "Action",
            released = "2024",
            rating = 4.5,
            backgroundImage = "url"
        )

        // When
        // Como o Room é lazy, ao executar uma operação, ele abre o banco automaticamente
        // (Aqui você precisará rodar dentro de um runBlocking se o seu DAO for suspend)
        kotlinx.coroutines.runBlocking {
            dao.insertFavorite(game)
        }

        // Then
        assertTrue(db.isOpen)
    }
}