package com.cabral.gamesrating.usecase

import com.cabral.gamesrating.data.model.GamesResponse
import com.cabral.gamesrating.di.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    operator fun invoke(): Flow<GamesResponse?> {
        return repository.getAllGames()
    }
}