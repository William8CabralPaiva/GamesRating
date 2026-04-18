package com.cabral.meusjogosfavoritos.data.remote

import com.cabral.meusjogosfavoritos.domain.model.GameDetailResponse
import com.cabral.meusjogosfavoritos.domain.model.GamesResponse
import com.cabral.meusjogosfavoritos.domain.model.ScreenshotResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET("games")
    suspend fun getAllGames(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
        @Query("search") search: String,
    ): Response<GamesResponse?>

    @GET("games/{id}")
    suspend fun getGameById(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Response<GameDetailResponse?>

    @GET("games/{id}/screenshots")
    suspend fun getScreenshots(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Response<ScreenshotResponse?>
}