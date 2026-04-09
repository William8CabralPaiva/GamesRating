package com.cabral.gamesrating.data.repository

import com.cabral.gamesrating.di.LocalDataSource
import com.cabral.gamesrating.di.RemoteDataSource
import com.cabral.gamesrating.domain.model.GameDetailResponse
import com.cabral.gamesrating.domain.model.ScreenshotResponse
import com.cabral.gamesrating.ui.model.GameUi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GamesRepositoryImplTest {

    private lateinit var repository: GamesRepositoryImpl
    private val remoteDataSource: RemoteDataSource = mockk()
    private val localDataSource: LocalDataSource = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        repository = GamesRepositoryImpl(
            remoteDataSource,
            localDataSource,
            testDispatcher
        )
    }

    @Test
    fun getGameById_shouldEmitGameDetailResponse_whenSuccessful() = runTest {
        // Given
        val gameId = 1
        val expectedResponse = mockk<GameDetailResponse>()
        coEvery { remoteDataSource.getGameById(gameId, any()) } returns Response.success(
            expectedResponse
        )

        // When
        val result = repository.getGameById(gameId).first()

        // Then
        assertEquals(expectedResponse, result)
        coVerify { remoteDataSource.getGameById(gameId, any()) }
    }

    @Test(expected = Exception::class)
    fun getGameById_shouldThrowException_whenApiReturnsError() = runTest {
        // Given
        val gameId = 1
        coEvery { remoteDataSource.getGameById(gameId, any()) } returns Response.error(
            404,
            mockk(relaxed = true)
        )

        // When
        repository.getGameById(gameId).first()

        // Then
    }

    @Test
    fun getScreenshots_shouldEmitScreenshotResponse_whenSuccessful() = runTest {
        // Given
        val gameId = 1
        val expectedScreenshots = mockk<ScreenshotResponse>()
        coEvery { remoteDataSource.getScreenshots(gameId, any()) } returns Response.success(
            expectedScreenshots
        )

        // When
        val result = repository.getScreenshots(gameId).first()

        // Then
        assertEquals(expectedScreenshots, result)
        coVerify { remoteDataSource.getScreenshots(gameId, any()) }
    }

    @Test
    fun saveFavoriteGame_shouldCallInsertFavoriteOnLocalDataSource() = runTest {
        // Given
        val gameUi = mockk<GameUi>(relaxed = true)
        coEvery { localDataSource.insertFavorite(any()) } returns Unit

        // When
        repository.saveFavoriteGame(gameUi)

        // Then
        coVerify { localDataSource.insertFavorite(any()) }
    }

    @Test
    fun deleteFavoriteGame_shouldCallDeleteFavoriteByIdOnLocalDataSource() = runTest {
        // Given
        val gameId = 123
        coEvery { localDataSource.deleteFavoriteById(gameId) } returns Unit

        // When
        repository.deleteFavoriteGame(gameId)

        // Then
        coVerify { localDataSource.deleteFavoriteById(gameId) }
    }

    @Test
    fun getAllFavorites_shouldMapAndReturnGameUiList() = runTest {
        // Given
        coEvery { localDataSource.getAllFavorites() } returns flowOf(emptyList())

        // When
        val result = repository.getAllFavorites().first()

        // Then
        assertEquals(0, result.size)
        coVerify { localDataSource.getAllFavorites() }
    }
}