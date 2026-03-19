package com.cabral.gamesrating.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabral.gamesrating.ui.model.toGameUiList
import com.cabral.gamesrating.usecase.GetAllGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GamesSharedViewModel(
    private val getAllGamesUseCase: GetAllGamesUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow<GamesUiState>(GamesUiState.Loading)
    val uiState: StateFlow<GamesUiState> = _uiState.asStateFlow()

//    private var _cachedGame: Game? = null
//    val cachedGame: Game? get() = _cachedGame

    init {
        fetchGames()
    }

    fun fetchGames() {
        viewModelScope.launch {
            _uiState.value = GamesUiState.Loading

            getAllGamesUseCase()
                .catch { exception ->
                    // Trata erro de rede ou parsing
                    _uiState.value = GamesUiState.Error(exception.message ?: "Erro desconhecido")
                }
                .collect { gameResponse ->
                    val gameList = gameResponse?.results?.toGameUiList() ?: emptyList()
                    if (gameList.isNotEmpty()) {
                        // _cachedGame = gameResponse.results
                        _uiState.value = GamesUiState.Success(gameList)
                    } else {
                        _uiState.value = GamesUiState.Empty
                    }
                }
        }
    }
}