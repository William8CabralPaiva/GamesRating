package com.cabral.gamesrating.ui.gamedetail

import com.cabral.gamesrating.ui.model.GameDetailScreenshots

sealed class GamesUiState {
    object Loading : GamesUiState()
    data class Success(val game: GameDetailScreenshots) : GamesUiState()
    data class Error(val message: String) : GamesUiState()
}