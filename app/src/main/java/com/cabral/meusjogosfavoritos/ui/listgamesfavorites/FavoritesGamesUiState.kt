package com.cabral.meusjogosfavoritos.ui.listgamesfavorites

import com.cabral.meusjogosfavoritos.ui.model.GameUi

sealed class FavoritesGamesUiState {
    object Loading : FavoritesGamesUiState()
    data class Success(val listGames: List<GameUi>?) : FavoritesGamesUiState()
    data class Error(val message: String) : FavoritesGamesUiState()
}