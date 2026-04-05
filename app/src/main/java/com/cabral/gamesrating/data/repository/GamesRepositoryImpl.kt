package com.cabral.gamesrating.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cabral.gamesrating.BuildConfig
import com.cabral.gamesrating.data.local.GameFavoriteEntity
import com.cabral.gamesrating.data.local.toGameFavoriteEntity
import com.cabral.gamesrating.data.local.toGameUi
import com.cabral.gamesrating.data.paging.GamesPagingSource
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.di.LocalDataSource
import com.cabral.gamesrating.domain.model.GameDetailResponse
import com.cabral.gamesrating.domain.model.ScreenshotResponse
import com.cabral.gamesrating.di.RemoteDataSource
import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameDetailScreenshots
import com.cabral.gamesrating.ui.model.GameUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GamesRepository {

    override fun getAllGames(search: String): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { GamesPagingSource(remoteDataSource, search) }
        ).flow.flowOn(dispatcher)
    }

    override fun getGameById(id: Int): Flow<GameDetailResponse> = flow {
        val response = remoteDataSource.getGameById(id, BuildConfig.API_KEY)
        if (response.isSuccessful) {
            response.body()?.let { game ->
                emit(game)
            } ?: throw Exception("Corpo da resposta vazio")
        } else {
            throw Exception("Erro na API: ${response.code()}")
        }
    }.flowOn(dispatcher)


    override fun getScreenshots(id: Int): Flow<ScreenshotResponse> = flow {
        val response = remoteDataSource.getScreenshots(id, BuildConfig.API_KEY)

        if (response.isSuccessful) {
            response.body()?.let { game ->
                emit(game)
            } ?: throw Exception("Corpo da resposta vazio")
        } else {
            throw Exception("Erro na API: ${response.code()}")
        }
    }.flowOn(dispatcher)

    override fun getAllFavorites(): Flow<List<GameUi>> {
        return localDataSource.getAllFavorites()
            .map { entities -> entities.map { it.toGameUi() } }
            .flowOn(dispatcher)
    }

    override suspend fun saveFavoriteGame(game: GameUi) {
        withContext(dispatcher) {
            localDataSource.insertFavorite(game.toGameFavoriteEntity())
        }
    }


    override suspend fun deleteFavoriteGame(id: Int) {
        withContext(dispatcher) {
            localDataSource.deleteFavoriteById(id)
        }
    }

}