package com.cabral.gamesrating.domain.repository

import androidx.paging.PagingData
import com.cabral.gamesrating.data.local.GameFavoriteEntity
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.domain.model.GameDetailResponse
import com.cabral.gamesrating.domain.model.ScreenshotResponse
import com.cabral.gamesrating.ui.model.GameUi
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getAllGames(search: String): Flow<PagingData<Game>>
    fun getGameById(id: Int): Flow<GameDetailResponse>
    fun getScreenshots(id: Int): Flow<ScreenshotResponse>
    fun getAllFavorites(): Flow<List<GameUi>>
    suspend fun saveFavoriteGame(game: GameUi): Unit
    suspend fun deleteFavoriteGame(id: Int): Unit
    suspend fun updateFavoritesOrder(games: List<GameUi>)
}