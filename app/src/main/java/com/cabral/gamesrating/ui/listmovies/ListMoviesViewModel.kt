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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    getAllGamesUseCase: GetAllGamesUseCase,
) : ViewModel() {

    val games: Flow<PagingData<GameUi>> = getAllGamesUseCase()
        .map { pagingData -> pagingData.map { it.toGameUi() } }
        .cachedIn(viewModelScope)
}