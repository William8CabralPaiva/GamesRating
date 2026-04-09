package com.cabral.gamesrating.di

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cabral.gamesrating.data.remote.GamesApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit

class NetworkModuleTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `provideRetrofit should configure retrofit with correct base url`() {
        // GIVEN
        val expectedBaseUrl = "https://api.rawg.io/api/"

        // WHEN
        val retrofit = NetworkModule.provideRetrofit()

        // THEN
        assertNotNull(retrofit)
        assertEquals(expectedBaseUrl, retrofit.baseUrl().toString())
    }

    @Test
    fun `provideMoviesApi should return gamesApi from retrofit`() {
        // GIVEN
        val retrofit = mockk<Retrofit>()
        val expectedApi = mockk<GamesApi>()

        every { retrofit.create(GamesApi::class.java) } returns expectedApi

        // WHEN
        val result = NetworkModule.provideMoviesApi(retrofit)

        // THEN
        assertNotNull(result)
        assertEquals(expectedApi, result)
        verify(exactly = 1) { retrofit.create(GamesApi::class.java) }
    }
}