package com.cabral.meusjogosfavoritos.domain.repository

interface TranslationRepository {
    suspend fun translate(text: String, targetLang: String): String
}