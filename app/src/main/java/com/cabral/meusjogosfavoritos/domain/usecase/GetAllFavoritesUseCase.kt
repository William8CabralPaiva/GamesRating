package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    operator fun invoke(): Flow<List<GameUi>> {
        return repository.getAllFavorites()
    }
}
