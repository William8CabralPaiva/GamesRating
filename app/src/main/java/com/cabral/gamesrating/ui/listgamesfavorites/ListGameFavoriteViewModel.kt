package com.cabral.gamesrating.ui.listgamesfavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabral.gamesrating.domain.usecase.DeleteFavoriteGameUseCase
import com.cabral.gamesrating.domain.usecase.GetAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListGameFavoriteViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteFavoriteGameUseCase: DeleteFavoriteGameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesGamesUiState>(FavoritesGamesUiState.Loading)
    val uiState: StateFlow<FavoritesGamesUiState> = _uiState.asStateFlow()

    init {
        fetchGames()
    }

    private fun fetchGames() {
        viewModelScope.launch {
            _uiState.value = FavoritesGamesUiState.Loading

            getAllFavoritesUseCase().catch { exception ->
                _uiState.value =
                    FavoritesGamesUiState.Error(exception.message ?: "Erro desconhecido")
            }.collect { gameResponse ->
                _uiState.value = FavoritesGamesUiState.Success(gameResponse)
            }
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            deleteFavoriteGameUseCase(id)
        }
    }
}