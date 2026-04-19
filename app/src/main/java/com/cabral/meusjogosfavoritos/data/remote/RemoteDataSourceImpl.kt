package com.cabral.meusjogosfavoritos.data.remote

import com.cabral.meusjogosfavoritos.di.RemoteDataSource
import com.cabral.meusjogosfavoritos.domain.model.GameDetailResponse
import com.cabral.meusjogosfavoritos.domain.model.GamesResponse
import com.cabral.meusjogosfavoritos.domain.model.ScreenshotResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val gamesApi: GamesApi
) : RemoteDataSource {

    override suspend fun getAllGames(
        key: String,
        page: Int,
        pageSize: Int,
        search: String,
    ): Response<GamesResponse?> {
        return gamesApi.getAllGames(key, page, pageSize, search)
    }

    override suspend fun getGameById(
        id: Int,
        apiKey: String
    ): Response<GameDetailResponse?> {
        return gamesApi.getGameById(id, apiKey)
    }

    override suspend fun getScreenshots(
        id: Int,
        apiKey: String
    ): Response<ScreenshotResponse?> {
        return gamesApi.getScreenshots(id, apiKey)
    }
}
