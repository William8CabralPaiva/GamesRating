package com.cabral.gamesrating.domain.usecase

import com.cabral.gamesrating.data.local.GameFavoriteEntity
import com.cabral.gamesrating.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    operator fun invoke(): Flow<List<GameFavoriteEntity>> {
        return repository.getAllFavorites()
    }
}
