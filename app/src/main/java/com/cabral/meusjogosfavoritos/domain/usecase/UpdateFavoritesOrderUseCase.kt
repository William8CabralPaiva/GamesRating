package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import javax.inject.Inject

class UpdateFavoritesOrderUseCase @Inject constructor(
    private val repository: GamesRepository
) {
    suspend operator fun invoke(games: List<GameUi>) {
        repository.updateFavoritesOrder(games)
    }
}
