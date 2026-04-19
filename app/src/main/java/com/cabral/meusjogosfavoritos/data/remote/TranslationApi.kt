package com.cabral.meusjogosfavoritos.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("get")
    suspend fun translate(
        @Query("q") text: String,
        @Query("langpair") langPair: String
    ): Response<TranslationResponse>
}

data class TranslationResponse(
    val responseData: ResponseData
)

data class ResponseData(
    val translatedText: String
)
