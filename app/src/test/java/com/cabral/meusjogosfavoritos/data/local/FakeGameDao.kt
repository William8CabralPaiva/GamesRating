package com.cabral.meusjogosfavoritos.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGameDao : GameDao {

    private val favorites = mutableListOf<GameFavoriteEntity>()
    private val flow = MutableStateFlow<List<GameFavoriteEntity>>(emptyList())

    override suspend fun insertFavorite(game: GameFavoriteEntity) {
        favorites.removeAll { it.id == game.id }

        val newOrderId = (favorites.maxOfOrNull { it.orderId ?: 0 } ?: 0) + 1

        val gameWithOrder = game.copy(orderId = newOrderId)

        favorites.add(gameWithOrder)

        emitSorted()
    }

    override suspend fun deleteFavoriteById(gameId: Int) {
        favorites.removeAll { it.id == gameId }
        emitSorted()
    }

    override suspend fun updateFavorites(favorites: List<GameFavoriteEntity>) {
        favorites.forEach { updatedGame ->
            val index = this.favorites.indexOfFirst { it.id == updatedGame.id }
            if (index != -1) {
                this.favorites[index] = updatedGame
            }
        }
        emitSorted()
    }

    override fun getAllFavorites(): Flow<List<GameFavoriteEntity>> {
        return flow
    }

    private fun emitSorted() {
        flow.value = favorites.sortedBy { it.orderId }
    }
}