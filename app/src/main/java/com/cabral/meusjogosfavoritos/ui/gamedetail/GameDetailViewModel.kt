package com.cabral.meusjogosfavoritos.ui.gamedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cabral.meusjogosfavoritos.domain.usecase.GetGameDetailByIdUseCase
import com.cabral.meusjogosfavoritos.domain.usecase.SaveImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGameDetailByIdUseCase: GetGameDetailByIdUseCase,
    private val saveImageUseCase: SaveImageUseCase
) : ViewModel() {

    private val _eventFlow = Channel<String>()
    val eventFlow = _eventFlow.receiveAsFlow()

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
                    _uiState.value = GamesUiState.Error(exception.message ?: "Erro desconhecido")
                }
                .collect { gameResponse ->
                    _uiState.value = GamesUiState.Success(gameResponse)
                }
        }
    }

    fun downloadImage(imageUrl: String, fileName: String) {
        viewModelScope.launch {
            val result = saveImageUseCase(imageUrl, fileName)
            result.onSuccess {
                _eventFlow.send("Imagem salva na galeria!")
            }.onFailure{
                _eventFlow.send("Erro ao salvar imagem: ${it.message}")
            }
        }
    }

}