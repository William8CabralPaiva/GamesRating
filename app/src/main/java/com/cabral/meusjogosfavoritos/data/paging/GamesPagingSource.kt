package com.cabral.meusjogosfavoritos.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cabral.meusjogosfavoritos.BuildConfig
import com.cabral.meusjogosfavoritos.data.model.Game
import com.cabral.meusjogosfavoritos.di.RemoteDataSource

class GamesPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val search: String?,
) : PagingSource<Int, Game>() {

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        val page = params.key ?: STARTING_PAGE

        return try {
            val response = remoteDataSource.getAllGames(
                key = BuildConfig.API_KEY,
                page = page,
                pageSize = params.loadSize,
                search = search ?: ""
            )

            if (response.isSuccessful) {
                val games = response.body()?.results ?: emptyList()
                val hasNext = response.body()?.next != null

                LoadResult.Page(
                    data = games,
                    prevKey = if (page == STARTING_PAGE) null else page - 1,
                    nextKey = if (hasNext) page + 1 else null,
                )
            } else {
                LoadResult.Error(Exception("Erro na API: ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE = 1
    }
}