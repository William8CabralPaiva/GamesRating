package com.cabral.gamesrating.data.local.datasource

import com.cabral.gamesrating.data.local.FakeGameDao
import com.cabral.gamesrating.data.local.GameFavoriteEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LocalDataSourceImplTest {

    private lateinit var fakeDao: FakeGameDao
    private lateinit var localDataSource: LocalDataSourceImpl

    @Before
    fun setup() {
        fakeDao = FakeGameDao()
        localDataSource = LocalDataSourceImpl(fakeDao)
    }

    @Test
    fun `insertFavorite should add item to favorites`() = runTest {
        val game = GameFavoriteEntity(
            id = 1,
            orderId = null,
            name = "Test Game",
            genres = "Action",
            released = "2024",
            rating = 4.5,
            backgroundImage = "url"
        )

        localDataSource.insertFavorite(game)

        val result = localDataSource.getAllFavorites().first()

        assertEquals(1, result.size)
        assertEquals("Test Game", result.first().name)
    }

    @Test
    fun `deleteFavoriteById should remove item`() = runTest {
        val game = GameFavoriteEntity(
            id = 1,
            orderId = null,
            name = "Test Game",
            genres = null,
            released = null,
            rating = null,
            backgroundImage = null
        )

        localDataSource.insertFavorite(game)
        localDataSource.deleteFavoriteById(1)

        val result = localDataSource.getAllFavorites().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllFavorites should emit updated list`() = runTest {
        val game1 = GameFavoriteEntity(
            id = 1,
            orderId = null,
            name = "Game 1",
            genres = null,
            released = null,
            rating = null,
            backgroundImage = null
        )

        val game2 = GameFavoriteEntity(
            id = 2,
            orderId = null,
            name = "Game 2",
            genres = null,
            released = null,
            rating = null,
            backgroundImage = null
        )

        localDataSource.insertFavorite(game1)
        localDataSource.insertFavorite(game2)

        val result = localDataSource.getAllFavorites().first()

        assertEquals(2, result.size)
    }
}