package com.cabral.meusjogosfavoritos.domain.usecase

import androidx.paging.PagingData
import com.cabral.meusjogosfavoritos.data.model.Game
import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(
    private val repository: GamesRepository,
) {
    operator fun invoke(search: String): Flow<PagingData<Game>> {
        return repository.getAllGames(search)
    }
}