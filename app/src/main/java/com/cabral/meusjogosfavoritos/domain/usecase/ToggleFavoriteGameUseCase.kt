package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.ui.model.GameUi
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