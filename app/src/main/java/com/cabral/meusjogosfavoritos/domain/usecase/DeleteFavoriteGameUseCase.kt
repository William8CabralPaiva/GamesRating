package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import javax.inject.Inject

class DeleteFavoriteGameUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteFavoriteGame(id)
    }
}