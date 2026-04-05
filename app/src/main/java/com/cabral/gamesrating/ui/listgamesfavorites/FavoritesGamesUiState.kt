package com.cabral.gamesrating.ui.listgamesfavorites

import com.cabral.gamesrating.ui.model.GameUi

sealed class FavoritesGamesUiState {
    object Loading : FavoritesGamesUiState()
    data class Success(val listGames: List<GameUi>?) : FavoritesGamesUiState()
    data class Error(val message: String) : FavoritesGamesUiState()
}