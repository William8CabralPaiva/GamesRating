package com.cabral.gamesrating.ui.listmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.model.toGameUi
import com.cabral.gamesrating.usecase.GetAllGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    getAllGamesUseCase: GetAllGamesUseCase,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val games: Flow<PagingData<GameUi>> = searchQuery
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getAllGamesUseCase(query)
        }
        .map { pagingData -> pagingData.map { it.toGameUi() } }
        .cachedIn(viewModelScope)

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }
}