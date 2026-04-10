package com.cabral.gamesrating.domain.usecase

import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    operator fun invoke(): Flow<List<GameUi>> {
        return repository.getAllFavorites()
    }
}
