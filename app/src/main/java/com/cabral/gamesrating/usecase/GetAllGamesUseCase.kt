package com.cabral.gamesrating.usecase

import androidx.paging.PagingData
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.di.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    operator fun invoke(search: String): Flow<PagingData<Game>> {
        return repository.getAllGames(search)
    }
}