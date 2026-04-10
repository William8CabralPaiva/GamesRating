package com.cabral.gamesrating.ui.listgamesfavorites

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.cabral.gamesrating.ui.model.GameUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ListGamesFavoriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_show_loading_state_when_uiState_is_loading() {
        // Given
        val uiState = FavoritesGamesUiState.Loading

        // When
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("loading_state").assertIsDisplayed()
    }

    @Test
    fun should_show_error_state_when_uiState_is_error() {
        // Given
        val errorMessage = "Erro ao carregar favoritos"
        val uiState = FavoritesGamesUiState.Error(errorMessage)

        // When
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("error_state").assertIsDisplayed()
        // Verifica se a string de erro do R.string.error_list aparece
        composeTestRule.onNodeWithText("Erro ao buscar os itens").assertIsDisplayed()
    }

    @Test
    fun should_show_empty_state_when_list_is_empty() {
        // Given
        val uiState = FavoritesGamesUiState.Success(emptyList())

        // When
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lista Vazia").assertIsDisplayed()
    }

    @Test
    fun should_display_favorites_list_when_uiState_is_success() {
        // Given
        val gameName = "Elden Ring"
        val fakeGames = listOf(
            GameUi(1, gameName, "2022-02-25", "", 4.9, "Ação", true)
        )
        val uiState = FavoritesGamesUiState.Success(fakeGames)

        // When
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("favorite_list").assertIsDisplayed()
        composeTestRule.onNodeWithText(gameName).assertIsDisplayed()
    }

    @Test
    fun should_trigger_remove_favorite_when_click_favorite_icon() {
        // Given
        var capturedId = -1
        val gameId = 99
        val fakeGames = listOf(
            GameUi(gameId, "God of War", "", "", 5.0, "", true)
        )
        val uiState = FavoritesGamesUiState.Success(fakeGames)

        composeTestRule.setContent {
            ListGameFavoriteContent(
                uiState = uiState,
                onClickFavorite = { capturedId = it }
            )
        }

        // When
        // Procura pelo botão de favoritar (usando contentDescription do seu XML)
        composeTestRule.onNodeWithContentDescription("Botão para favoritar")
            .performClick()

        // Then
        assert(capturedId == gameId)
    }
}