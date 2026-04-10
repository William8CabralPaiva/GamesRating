package com.cabral.gamesrating.domain.usecase

import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameUi
import javax.inject.Inject

class ToggleFavoriteGameUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(addGame: Boolean, game: GameUi) {
        if (addGame) {
            repository.saveFavoriteGame(game)
        } else {
            repository.deleteFavoriteGame(game.id)
        }
    }
}