package com.cabral.gamesrating.ui.gamedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabral.gamesrating.usecase.GetGameDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameDetailByIdUseCase: GetGameDetailByIdUseCase,
) : ViewModel() {

    val gameId: Int = savedStateHandle["gameId"] ?: 0

    private val _uiState = MutableStateFlow<GamesUiState>(GamesUiState.Loading)
    val uiState: StateFlow<GamesUiState> = _uiState.asStateFlow()

    init {
        fetchGames(gameId)
    }

    private fun fetchGames(id: Int) {
        viewModelScope.launch {
            _uiState.value = GamesUiState.Loading

            getGameDetailByIdUseCase(id)
                .catch { exception ->
                    // Trata erro de rede ou parsing
                    _uiState.value = GamesUiState.Error(exception.message ?: "Erro desconhecido")
                }
                .collect { gameResponse ->
                    _uiState.value = GamesUiState.Success(gameResponse)
                }
        }
    }

}