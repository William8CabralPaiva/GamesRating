package com.cabral.gamesrating.di

import com.cabral.gamesrating.BuildConfig
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.data.model.GamesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : MoviesRepository {

    override fun getAllGames(): Flow<GamesResponse?> = flow {
        val response = moviesApi.getAllGames(BuildConfig.API_KEY)

        if (response.isSuccessful) {
            response.body()?.let { game ->
                emit(game)
            } ?: throw Exception("Corpo da resposta vazio")
        } else {
            throw Exception("Erro na API: ${response.code()}")
        }
    }.flowOn(dispatcher)
}