package com.cabral.gamesrating.di

import androidx.paging.PagingData
import com.cabral.gamesrating.data.model.Game
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getAllGames(): Flow<PagingData<Game>>
}