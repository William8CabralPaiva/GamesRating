package com.cabral.gamesrating.domain.usecase

import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameUi
import javax.inject.Inject

class DeleteFavoriteGameUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteFavoriteGame(id)
    }
}