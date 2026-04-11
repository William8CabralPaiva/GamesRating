package com.cabral.gamesrating.data.local.datasource

import com.cabral.gamesrating.data.local.GameDao
import com.cabral.gamesrating.data.local.GameFavoriteEntity
import com.cabral.gamesrating.di.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gameDao: GameDao
) : LocalDataSource {

    override suspend fun insertFavorite(game: GameFavoriteEntity) {
        gameDao.insertFavorite(game)
    }

    override suspend fun deleteFavoriteById(gameId: Int) {
        gameDao.deleteFavoriteById(gameId)
    }

    override fun getAllFavorites(): Flow<List<GameFavoriteEntity>> {
        return gameDao.getAllFavorites()
    }

    override suspend fun updateFavoritesOrder(favorites: List<GameFavoriteEntity>) {
        gameDao.updateFavoritesOrder(favorites)
    }
}
