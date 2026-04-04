package com.cabral.gamesrating.domain.repository

import androidx.paging.PagingData
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.domain.model.GameDetailResponse
import com.cabral.gamesrating.domain.model.ScreenshotResponse
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getAllGames(search: String): Flow<PagingData<Game>>
    fun getGameById(id: Int): Flow<GameDetailResponse>
    fun getScreenshots(id: Int): Flow<ScreenshotResponse>
}