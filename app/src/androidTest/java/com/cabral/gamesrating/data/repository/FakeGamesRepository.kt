package com.cabral.gamesrating.data.repository

import androidx.paging.PagingData
import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.domain.model.GameDetailResponse
import com.cabral.gamesrating.domain.model.ScreenshotResponse
import com.cabral.gamesrating.domain.repository.GamesRepository
import com.cabral.gamesrating.ui.model.GameUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeGamesRepository @Inject constructor() : GamesRepository {

    private val gameDetailFlow = MutableSharedFlow<GameDetailResponse>(replay = 1)
    private val screenshotsFlow = MutableSharedFlow<ScreenshotResponse>(replay = 1)
    private val favoritesFlow = MutableSharedFlow<List<GameUi>>(replay = 1)

    fun emitGameDetail(game: GameDetailResponse) {
        gameDetailFlow.tryEmit(game)
    }

    fun emitScreenshots(screenshots: ScreenshotResponse) {
        screenshotsFlow.tryEmit(screenshots)
    }

    override fun getAllGames(search: String): Flow<PagingData<Game>> {
        return flowOf(PagingData.empty())
    }

    override fun getGameById(id: Int): Flow<GameDetailResponse> = gameDetailFlow

    override fun getScreenshots(id: Int): Flow<ScreenshotResponse> = screenshotsFlow

    override fun getAllFavorites(): Flow<List<GameUi>> = favoritesFlow

    override suspend fun saveFavoriteGame(game: GameUi) {
        // Implementar se necessário para testes de favoritos
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        // Implementar se necessário para testes de favoritos
    }
}
