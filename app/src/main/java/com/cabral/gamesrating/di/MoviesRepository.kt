package com.cabral.gamesrating.di

import androidx.paging.PagingData
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.data.model.GameDetailResponse
import com.cabral.gamesrating.data.model.ScreenshotResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getAllGames(search: String): Flow<PagingData<Game>>
    fun getGameById(id: Int): Flow<GameDetailResponse>
    fun getScreenshots(id: Int): Flow<ScreenshotResponse>

}