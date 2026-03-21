package com.cabral.gamesrating.ui.gamedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabral.gamesrating.ui.listmovies.GamesUiState
import com.cabral.gamesrating.ui.model.toGameUiList
import com.cabral.gamesrating.usecase.GameDetailByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameDetailViewModel @Inject constructor(
    //private val gameDetailByIdUseCase: GameDetailByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val gameId: Int = savedStateHandle["gameId"] ?: 0

    init {
        val a = gameId
    }

//    private val _uiState = MutableStateFlow<GamesUiState>(GamesUiState.Loading)
//    val uiState: StateFlow<GamesUiState> = _uiState.asStateFlow()
//
//
//    fun fetchGames(id: Int) {
//        viewModelScope.launch {
//            _uiState.value = GamesUiState.Loading
//
//            gameDetailByIdUseCase(id)
//                .catch { exception ->
//                    // Trata erro de rede ou parsing
//                    _uiState.value = GamesUiState.Error(exception.message ?: "Erro desconhecido")
//                }
//                .collect { gameResponse ->
//                    val gameList = gameResponse?.results?.toGameUiList() ?: emptyList()
//                    if (gameList.isNotEmpty()) {
//                        // _cachedGame = gameResponse.results
//                        _uiState.value = GamesUiState.Success(gameList)
//                    } else {
//                        _uiState.value = GamesUiState.Empty
//                    }
//                }
//        }
//    }

}