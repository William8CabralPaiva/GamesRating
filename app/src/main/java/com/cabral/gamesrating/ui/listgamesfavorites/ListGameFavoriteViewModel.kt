package com.cabral.gamesrating.ui.listgamesfavorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabral.gamesrating.domain.usecase.DeleteFavoriteGameUseCase
import com.cabral.gamesrating.domain.usecase.GetAllFavoritesUseCase
import com.cabral.gamesrating.domain.usecase.UpdateFavoritesOrderUseCase
import com.cabral.gamesrating.ui.model.GameUi
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
    private val updateFavoritesOrderUseCase: UpdateFavoritesOrderUseCase,
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
                _uiState.value = FavoritesGamesUiState.Error(exception.message ?: "Erro desconhecido")
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

    fun onMove(fromIndex: Int, toIndex: Int) {
        val currentState = _uiState.value
        if (currentState is FavoritesGamesUiState.Success) {
            val newList = currentState.listGames?.toMutableList() ?: return
            if (fromIndex !in newList.indices || toIndex !in newList.indices) return

            val item = newList.removeAt(fromIndex)
            newList.add(toIndex, item)

            _uiState.value = currentState.copy(listGames = newList)
        }
    }

    fun onDragEnd() {
        val currentState = _uiState.value
        if (currentState is FavoritesGamesUiState.Success) {
            val list = currentState.listGames ?: return
            // Atualiza o orderId de cada item baseado na nova posição da lista
            val updatedList = list.mapIndexed { index, game ->
                game.copy(orderId = index)
            }
            updateOrder(updatedList)
        }
    }

    private fun updateOrder(games: List<GameUi>) {
        viewModelScope.launch {
            updateFavoritesOrderUseCase(games)
        }
    }
}
