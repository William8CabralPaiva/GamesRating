package com.cabral.meusjogosfavoritos.data.paging

import androidx.paging.PagingSource
import com.cabral.meusjogosfavoritos.data.model.Game
import com.cabral.meusjogosfavoritos.di.RemoteDataSource
import com.cabral.meusjogosfavoritos.domain.model.GamesResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GamesPagingSourceTest {

    private lateinit var pagingSource: GamesPagingSource
    private val remoteDataSource: RemoteDataSource = mockk()
    private val search = "batman"

    @Before
    fun setup() {
        pagingSource = GamesPagingSource(remoteDataSource, search)
    }

    @Test
    fun load_shouldReturnPage_whenSuccessfulLoad() = runTest {
        // Given
        val games = listOf(mockk<Game>())
        val response = GamesResponse(
            count = 1,
            next = "next_page_url",
            previous = null,
            results = games
        )
        coEvery {
            remoteDataSource.getAllGames(any(), any(), any(), any())
        } returns Response.success(response)

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        val expected = PagingSource.LoadResult.Page(
            data = games,
            prevKey = null,
            nextKey = 2
        )
        assertEquals(expected, result)
    }

    @Test
    fun load_shouldReturnError_whenApiThrowsException() = runTest {
        // Given
        val exception = RuntimeException("Network Error")
        coEvery {
            remoteDataSource.getAllGames(any(), any(), any(), any())
        } throws exception

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(exception, (result as PagingSource.LoadResult.Error).throwable)
    }

    @Test
    fun load_shouldReturnError_whenApiReturnsUnsuccessfulResponse() = runTest {
        // Given
        coEvery {
            remoteDataSource.getAllGames(any(), any(), any(), any())
        } returns Response.error(404, mockk(relaxed = true))

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun load_shouldReturnEmptyData_whenResultsAreNull() = runTest {
        // Given
        val response = GamesResponse(
            count = 0,
            next = null,
            previous = null,
            results = null
        )
        coEvery {
            remoteDataSource.getAllGames(any(), any(), any(), any())
        } returns Response.success(response)

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue((result as PagingSource.LoadResult.Page).data.isEmpty())
        assertEquals(null, result.nextKey)
    }
}