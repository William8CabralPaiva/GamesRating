package com.cabral.meusjogosfavoritos.ui.gamedetail

import com.cabral.meusjogosfavoritos.ui.model.GameDetailScreenshots

sealed class GamesUiState {
    object Loading : GamesUiState()
    data class Success(val game: GameDetailScreenshots) : GamesUiState()
    data class Error(val message: String) : GamesUiState()
}