package com.cabral.gamesrating.di

import com.cabral.gamesrating.data.model.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("games")
    suspend fun getAllGames(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
    ): Response<GamesResponse?>
}