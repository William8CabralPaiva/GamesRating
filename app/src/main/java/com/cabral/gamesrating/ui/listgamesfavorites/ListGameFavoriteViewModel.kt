//package com.cabral.gamesrating.ui.listgamesfavorites
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.paging.PagingData
//import androidx.paging.cachedIn
//import androidx.paging.map
//import com.cabral.gamesrating.ui.model.GameUi
//import com.cabral.gamesrating.ui.model.toGameUi
//import com.cabral.gamesrating.domain.usecase.GetAllGamesUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.FlowPreview
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.combine
//import kotlinx.coroutines.flow.debounce
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.flow.flatMapLatest
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.update
//import javax.inject.Inject
//
//@HiltViewModel
//class ListGameFavoriteViewModel @Inject constructor(
//    getAllGamesUseCase: GetAllGamesUseCase,
//) : ViewModel() {
//
//    private val _searchQuery = MutableStateFlow("")
//    val searchQuery: StateFlow<String> = _searchQuery
//
//    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())
//
//    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
//    val games: Flow<PagingData<GameUi>> = combine(
//        searchQuery.debounce(500).distinctUntilChanged(),
//        _favorites
//    ) { query, favorites -> query to favorites }
//        .flatMapLatest { (query, favorites) ->
//            getAllGamesUseCase(query)
//                .map { pagingData ->
//                    pagingData.map { it.toGameUi(isFavorite = it.id in favorites) }
//                }
//        }
//        .cachedIn(viewModelScope)
//
//    fun updateSearch(query: String) {
//        _searchQuery.value = query
//    }
//
//    fun toggleFavorite(id: Int) {
//        _favorites.update { current ->
//            if (id in current) current - id else current + id
//        }
//    }
//}