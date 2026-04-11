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

    // Lista interna para simular o banco de dados de favoritos
    private val favoriteGamesList = mutableListOf<GameUi>()

    // Lista interna para simular a resposta da API de jogos
    private var gamesList = listOf<Game>()

    fun emitGameDetail(game: GameDetailResponse) {
        gameDetailFlow.tryEmit(game)
    }

    fun emitScreenshots(screenshots: ScreenshotResponse) {
        screenshotsFlow.tryEmit(screenshots)
    }

    /**
     * Permite configurar quais jogos o repositório deve retornar nos testes.
     */
    fun setGames(games: List<Game>) {
        this.gamesList = games
    }

    override fun getAllGames(search: String): Flow<PagingData<Game>> {
        val filteredGames = if (search.isEmpty()) {
            gamesList
        } else {
            gamesList.filter { it.name?.contains(search, ignoreCase = true) == true }
        }
        return flowOf(PagingData.from(filteredGames))
    }

    override fun getGameById(id: Int): Flow<GameDetailResponse> = gameDetailFlow

    override fun getScreenshots(id: Int): Flow<ScreenshotResponse> = screenshotsFlow

    override fun getAllFavorites(): Flow<List<GameUi>> = favoritesFlow

    override suspend fun saveFavoriteGame(game: GameUi) {
        if (!favoriteGamesList.any { it.id == game.id }) {
            favoriteGamesList.add(game)
        }
        favoritesFlow.emit(favoriteGamesList.toList())
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        favoriteGamesList.removeAll { it.id == id }
        favoritesFlow.emit(favoriteGamesList.toList())
    }

    override suspend fun updateFavoritesOrder(games: List<GameUi>) {
        favoriteGamesList.clear()
        favoriteGamesList.addAll(games)
        favoritesFlow.emit(favoriteGamesList.toList())
    }
}