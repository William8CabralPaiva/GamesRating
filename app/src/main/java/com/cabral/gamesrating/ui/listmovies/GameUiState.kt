package com.cabral.gamesrating.ui.listmovies

import com.cabral.gamesrating.ui.model.GameUi

sealed class GamesUiState {
    object Loading : GamesUiState()
    data class Success(val game: List<GameUi>) : GamesUiState()
    data class Error(val message: String) : GamesUiState()
    object Empty : GamesUiState()
}