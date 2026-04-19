package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.data.model.Screenshot
import com.cabral.meusjogosfavoritos.domain.model.GameDetailResponse
import com.cabral.meusjogosfavoritos.domain.model.ScreenshotResponse
import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.domain.repository.TranslationRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
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
    private val gamesRepository: GamesRepository = mockk()
    private val translationRepository: TranslationRepository = mockk()

    @Before
    fun setup() {
        useCase = GetGameDetailByIdUseCase(gamesRepository, translationRepository)
    }

    @Test
    fun `invoke should not translate when language is en`() = runTest {
        // GIVEN
        val gameId = 1
        val lang = "en"
        val gameResponse = createMockGameResponse(gameId, "The Witcher", "A great game", "2015-05-19")
        val screenshotResponse = ScreenshotResponse(results = emptyList())

        every { gamesRepository.getGameById(gameId) } returns flowOf(gameResponse)
        every { gamesRepository.getScreenshots(gameId) } returns flowOf(screenshotResponse)

        // WHEN
        val result = useCase(gameId, lang).first()

        // THEN
        assertEquals("The Witcher", result.name)
        assertEquals("A great game", result.description)
        assertEquals("2015-05-19", result.released)
    }

    @Test
    fun `invoke should translate description and format date when language is pt`() = runTest {
        // GIVEN
        val gameId = 1
        val lang = "pt"
        val gameName = "The Witcher"
        val originalDesc = "The Witcher is amazing"
        val translatedDesc = "The Witcher é incrível"
        
        val gameResponse = createMockGameResponse(gameId, gameName, originalDesc, "2015-05-19")
        val screenshotResponse = ScreenshotResponse(results = emptyList())

        every { gamesRepository.getGameById(gameId) } returns flowOf(gameResponse)
        every { gamesRepository.getScreenshots(gameId) } returns flowOf(screenshotResponse)
        
        // Simula a proteção do nome: "The Witcher" vira "_GAME_"
        coEvery { 
            translationRepository.translate("_GAME_ is amazing", lang) 
        } returns "_GAME_ é incrível"

        // WHEN
        val result = useCase(gameId, lang).first()

        // THEN
        assertEquals(gameName, result.name)
        assertEquals(translatedDesc, result.description)
        assertEquals("19/05/2015", result.released) // Data formatada
    }

    @Test
    fun `invoke should preserve game name even with different casing during translation`() = runTest {
        // GIVEN
        val gameId = 1
        val lang = "pt"
        val gameName = "Zelda"
        val originalDesc = "Playing zelda is fun" // case insensitive match
        
        val gameResponse = createMockGameResponse(gameId, gameName, originalDesc, "2023-05-12")
        val screenshotResponse = ScreenshotResponse(results = emptyList())

        every { gamesRepository.getGameById(gameId) } returns flowOf(gameResponse)
        every { gamesRepository.getScreenshots(gameId) } returns flowOf(screenshotResponse)
        
        coEvery { 
            translationRepository.translate("Playing _GAME_ is fun", lang) 
        } returns "Jogar _GAME_ é divertido"

        // WHEN
        val result = useCase(gameId, lang).first()

        // THEN
        assertEquals("Jogar Zelda é divertido", result.description)
    }

    private fun createMockGameResponse(id: Int, name: String, desc: String, released: String) = GameDetailResponse(
        id = id,
        name = name,
        description = desc,
        released = released,
        platforms = emptyList(),
        genres = emptyList(),
        rating = 4.5,
        background_image = "img.jpg",
        background_image_additional = null
    )
}
