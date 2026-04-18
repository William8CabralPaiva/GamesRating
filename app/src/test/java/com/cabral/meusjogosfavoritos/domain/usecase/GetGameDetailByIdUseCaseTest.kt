package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.data.model.Screenshot
import com.cabral.meusjogosfavoritos.domain.model.GameDetailResponse
import com.cabral.meusjogosfavoritos.domain.model.ScreenshotResponse
import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGameDetailByIdUseCaseTest {

    private lateinit var useCase: GetGameDetailByIdUseCase
    private val repository: GamesRepository = mockk()

    @Before
    fun setup() {
        useCase = GetGameDetailByIdUseCase(repository)
    }

    @Test
    fun invoke_shouldCombineGameAndScreenshots_whenSuccessful() = runTest {
        // Given
        val gameId = 1
        val gameResponse = mockk<GameDetailResponse> {
            every { id } returns gameId
            every { name } returns "The Witcher 3"
            every { description } returns "Great RPG"
            every { background_image } returns "bg1.jpg"
            every { background_image_additional } returns "bg2.jpg"
            every { platforms } returns emptyList()
            every { genres } returns emptyList()
            every { released } returns "2015"
            every { rating } returns 4.8
        }

        val screenshotResponse = ScreenshotResponse(
            results = listOf(Screenshot(id = 1, image = "ss1.jpg"))
        )

        every { repository.getGameById(gameId) } returns flowOf(gameResponse)
        every { repository.getScreenshots(gameId) } returns flowOf(screenshotResponse)

        // When
        val result = useCase(gameId).first()

        // Then
        assertEquals(gameId, result.id)
        assertEquals("The Witcher 3", result.name)
        assertNotNull(result.screenshots)
        result.screenshots?.let {
            assertTrue(result.screenshots.contains("bg1.jpg"))
            assertTrue(result.screenshots.contains("bg2.jpg"))
            assertTrue(result.screenshots.contains("ss1.jpg"))
            assertEquals(3, result.screenshots.size)
        }


        verify { repository.getGameById(gameId) }
        verify { repository.getScreenshots(gameId) }
    }

    @Test
    fun invoke_shouldHandleEmptyImages_whenMappingScreenshotsList() = runTest {
        // Given
        val gameId = 1
        val gameResponse = mockk<GameDetailResponse> {
            every { id } returns gameId
            every { name } returns "Minimal Game"
            every { description } returns "Desc"
            every { background_image } returns null
            every { background_image_additional } returns ""
            every { platforms } returns emptyList()
            every { genres } returns emptyList()
            every { released } returns "2024"
            every { rating } returns 4.0
        }

        val screenshotResponse = ScreenshotResponse(results = emptyList())

        every { repository.getGameById(gameId) } returns flowOf(gameResponse)
        every { repository.getScreenshots(gameId) } returns flowOf(screenshotResponse)

        // When
        val result = useCase(gameId).first()

        // Then
        assertNotNull(result.screenshots)
        result.screenshots?.let {
            assertTrue(result.screenshots.isEmpty())
        }
        verify { repository.getGameById(gameId) }
        verify { repository.getScreenshots(gameId) }
    }
}