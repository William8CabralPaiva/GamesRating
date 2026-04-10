package com.cabral.gamesrating.data.remote

import com.cabral.gamesrating.di.FakeGamesApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceImplTest {

    private lateinit var fakeApi: FakeGamesApi
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        fakeApi = FakeGamesApi()
        remoteDataSource = RemoteDataSourceImpl(fakeApi)
    }

    @Test
    fun `getAllGames should return fakeGamesResponse`() = runTest {
        // when
        val response = remoteDataSource.getAllGames(
            key = "fake_key",
            page = 1,
            pageSize = 20,
            search = ""
        )

        // then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(3, response.body()?.results?.size)
        assertEquals("The Elder Scrolls VI", response.body()?.results?.first()?.name)
    }

    @Test
    fun `getGameById should return correct game detail`() = runTest {
        // when
        val response = remoteDataSource.getGameById(
            id = 58781,
            apiKey = "fake_key"
        )

        // then
        assertTrue(response.isSuccessful)
        val body = response.body()

        assertNotNull(body)
        assertEquals(58781, body?.id)
        assertEquals("The Elder Scrolls VI", body?.name)
        assertEquals(4.86, body?.rating)
    }

    @Test
    fun `getGameById should return null when game not found`() = runTest {
        // when
        val response = remoteDataSource.getGameById(
            id = -1,
            apiKey = "fake_key"
        )

        // then
        assertTrue(response.isSuccessful)
        assertNull(response.body())
    }

    @Test
    fun `getScreenshots should return list of screenshots`() = runTest {
        // when
        val response = remoteDataSource.getScreenshots(
            id = 58781,
            apiKey = "fake_key"
        )

        // then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(2, response.body()?.results?.size)
    }
}