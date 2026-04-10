package com.cabral.gamesrating.ui.listgames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cabral.gamesrating.domain.usecase.GetAllFavoritesUseCase
import com.cabral.gamesrating.domain.usecase.GetAllGamesUseCase
import com.cabral.gamesrating.domain.usecase.ToggleFavoriteGameUseCase
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.model.toGameUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListGameViewModel @Inject constructor(
    private val getAllGamesUseCase: GetAllGamesUseCase,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val toggleFavoriteGameUseCase: ToggleFavoriteGameUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    init {
        viewModelScope.launch {
            getAllFavoritesUseCase().collect { favorites ->
                _favoriteIds.value = favorites.map { it.id }.toSet()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val games: Flow<PagingData<GameUi>> = _searchQuery
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getAllGamesUseCase(query).map { pagingData ->
                pagingData.map { game ->
                    game.toGameUi(false)
                }
            }
        }.cachedIn(viewModelScope)

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(isFavorite: Boolean, game: GameUi) {
        viewModelScope.launch {
            toggleFavoriteGameUseCase(isFavorite, game)
        }
    }
}