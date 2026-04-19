package com.cabral.meusjogosfavoritos.data.repository

import com.cabral.meusjogosfavoritos.data.remote.TranslationApi
import com.cabral.meusjogosfavoritos.domain.repository.TranslationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val translationApi: TranslationApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TranslationRepository {

    override suspend fun translate(text: String, targetLang: String): String {
        if (text.isBlank()) return text

        return withContext(dispatcher) {
            val chunks = splitTextIntoChunks(text, 450)
            
            // Usamos map (que é inline) para realizar as chamadas suspensas
            // e depois joinToString para juntar os resultados.
            chunks.map { chunk ->
                translateChunk(chunk, targetLang)
            }.joinToString(" ")
        }
    }

    private suspend fun translateChunk(chunk: String, targetLang: String): String {
        return try {
            val langPair = "en|$targetLang"
            val response = translationApi.translate(chunk, langPair)
            if (response.isSuccessful) {
                response.body()?.responseData?.translatedText ?: chunk
            } else {
                chunk
            }
        } catch (e: Exception) {
            chunk
        }
    }

    private fun splitTextIntoChunks(text: String, limit: Int): List<String> {
        val chunks = mutableListOf<String>()
        var currentIndex = 0

        while (currentIndex < text.length) {
            var endIndex = currentIndex + limit
            
            if (endIndex >= text.length) {
                chunks.add(text.substring(currentIndex))
                break
            }

            // Procura o último espaço dentro do limite para não cortar palavras ao meio
            val lastSpace = text.lastIndexOf(' ', endIndex)
            if (lastSpace > currentIndex) {
                endIndex = lastSpace
            }

            chunks.add(text.substring(currentIndex, endIndex).trim())
            currentIndex = endIndex + 1
        }
        return chunks
    }
}
