package com.cabral.meusjogosfavoritos.di

import com.cabral.meusjogosfavoritos.domain.model.GameDetailResponse
import com.cabral.meusjogosfavoritos.domain.model.GamesResponse
import com.cabral.meusjogosfavoritos.domain.model.ScreenshotResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getAllGames(
        key: String,
        page: Int,
        pageSize: Int = 20,
        search: String,
    ): Response<GamesResponse?>

    suspend fun getGameById(
        id: Int,
        apiKey: String
    ): Response<GameDetailResponse?>

    suspend fun getScreenshots(
        id: Int,
        apiKey: String
    ): Response<ScreenshotResponse?>
}