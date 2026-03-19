package com.cabral.gamesrating.di

import com.cabral.gamesrating.data.model.GamesResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getAllGames(): Flow<GamesResponse?>
}