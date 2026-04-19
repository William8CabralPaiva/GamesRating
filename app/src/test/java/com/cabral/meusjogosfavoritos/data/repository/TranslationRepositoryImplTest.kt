package com.cabral.meusjogosfavoritos.data.repository

import com.cabral.meusjogosfavoritos.data.remote.ResponseData
import com.cabral.meusjogosfavoritos.data.remote.TranslationApi
import com.cabral.meusjogosfavoritos.data.remote.TranslationResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class TranslationRepositoryImplTest {

    private lateinit var repository: TranslationRepositoryImpl
    private val translationApi: TranslationApi = mockk()

    @Before
    fun setup() {
        repository = TranslationRepositoryImpl(translationApi, Dispatchers.Unconfined)
    }

    @Test
    fun `translate should return original text when input is blank`() = runTest {
        // Given
        val text = "   "
        val targetLang = "pt"

        // When
        val result = repository.translate(text, targetLang)

        // Then
        assertEquals(text, result)
    }

    @Test
    fun `translate should return translated text when API call is successful`() = runTest {
        // Given
        val text = "Hello"
        val targetLang = "pt"
        val translatedText = "Olá"
        val response = Response.success(TranslationResponse(ResponseData(translatedText)))

        coEvery { translationApi.translate(text, "en|pt") } returns response

        // When
        val result = repository.translate(text, targetLang)

        // Then
        assertEquals(translatedText, result)
    }

    @Test
    fun `translate should split and join text when it exceeds limit`() = runTest {
        // Given
        // Cria um texto com mais de 450 caracteres mas menor que 900
        val part1 = "a".repeat(400) + " "
        val part2 = "b".repeat(400)
        val fullText = part1 + part2
        val targetLang = "pt"
        
        coEvery { translationApi.translate(any(), "en|pt") } answers {
            val chunk = it.invocation.args[0] as String
            Response.success(TranslationResponse(ResponseData("translated_$chunk")))
        }

        // When
        val result = repository.translate(fullText, targetLang)

        // Then
        // Verifica se o resultado contém os prefixos indicando que ambas as partes foram processadas
        val expected = "translated_${part1.trim()} translated_$part2"
        assertEquals(expected, result)
    }

    @Test
    fun `translate should return original text when API call fails`() = runTest {
        // Given
        val text = "Hello"
        val targetLang = "pt"
        
        coEvery { translationApi.translate(text, "en|pt") } returns Response.error(500, mockk(relaxed = true))

        // When
        val result = repository.translate(text, targetLang)

        // Then
        assertEquals(text, result)
    }

    @Test
    fun `translate should return original text when API throws exception`() = runTest {
        // Given
        val text = "Hello"
        val targetLang = "pt"
        
        coEvery { translationApi.translate(text, "en|pt") } throws Exception("Network Error")

        // When
        val result = repository.translate(text, targetLang)

        // Then
        assertEquals(text, result)
    }
}
