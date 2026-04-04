package com.cabral.gamesrating.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cabral.gamesrating.BuildConfig
import com.cabral.gamesrating.data.paging.GamesPagingSource
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.domain.model.GameDetailResponse
import com.cabral.gamesrating.domain.model.ScreenshotResponse
import com.cabral.gamesrating.data.remote.GamesApi
import com.cabral.gamesrating.domain.repository.GamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gamesApi: GamesApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GamesRepository {

    override fun getAllGames(search: String): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { GamesPagingSource(gamesApi, search) }
        ).flow.flowOn(dispatcher)
    }

    override fun getGameById(id: Int): Flow<GameDetailResponse> = flow {
        val response = gamesApi.getGameById(id, BuildConfig.API_KEY)
        response
        if (response.isSuccessful) {
            response.body()?.let { game ->
                emit(game)
            } ?: throw Exception("Corpo da resposta vazio")
        } else {
            throw Exception("Erro na API: ${response.code()}")
        }
    }.flowOn(dispatcher)


    override fun getScreenshots(id: Int): Flow<ScreenshotResponse> = flow {
        val response = gamesApi.getScreenshots(id, BuildConfig.API_KEY)

        if (response.isSuccessful) {
            response.body()?.let { game ->
                emit(game)
            } ?: throw Exception("Corpo da resposta vazio")
        } else {
            throw Exception("Erro na API: ${response.code()}")
        }
    }.flowOn(dispatcher)
}