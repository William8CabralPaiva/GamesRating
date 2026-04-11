package com.cabral.gamesrating.di

import com.cabral.gamesrating.data.local.GameFavoriteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertFavorite(game: GameFavoriteEntity)
    suspend fun deleteFavoriteById(gameId: Int)
    fun getAllFavorites(): Flow<List<GameFavoriteEntity>>
    suspend fun updateFavoritesOrder(favorites: List<GameFavoriteEntity>)
}