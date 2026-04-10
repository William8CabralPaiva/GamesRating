package com.cabral.gamesrating.ui.listgamesfavorites

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.cabral.gamesrating.ui.model.GameUi
import org.junit.Rule
import org.junit.Test

class ListGamesFavoriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun stateLoading_shouldShowLoadingState() {
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = FavoritesGamesUiState.Loading)
        }
        composeTestRule.onNodeWithTag("loading_state").assertIsDisplayed()
    }

    @Test
    fun stateSuccess_shouldShowListWithItems() {
        val gameName = "The Witcher 3"
        val favoriteGames = listOf(
            GameUi(1, gameName, "2015", "", 4.9, "RPG", true)
        )

        composeTestRule.setContent {
            ListGameFavoriteContent(
                uiState = FavoritesGamesUiState.Success(favoriteGames)
            )
        }

        composeTestRule.onNodeWithTag("favorite_list").assertIsDisplayed()
        composeTestRule.onNodeWithText(gameName).assertIsDisplayed()
    }

    @Test
    fun stateEmpty_shouldShowEmptyMessage() {
        composeTestRule.setContent {
            ListGameFavoriteContent(
                uiState = FavoritesGamesUiState.Success(emptyList())
            )
        }
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun stateError_shouldShowErrorMessage() {
        composeTestRule.setContent {
            ListGameFavoriteContent(
                uiState = FavoritesGamesUiState.Error("Erro generico")
            )
        }
        composeTestRule.onNodeWithTag("error_state").assertIsDisplayed()
    }

    @Test
    fun clickOnFavorite_shouldTriggerOnClickFavorite() {
        var clickedId = -1
        val gameId = 1
        val favoriteGames = listOf(
            GameUi(gameId, "Elden Ring", "2022", "", 4.9, "RPG", true)
        )

        composeTestRule.setContent {
            ListGameFavoriteContent(
                uiState = FavoritesGamesUiState.Success(favoriteGames),
                onClickFavorite = { clickedId = it }
            )
        }

        // Clica no botão de favorito (ícone de coração) dentro do GameItem
        // Como o GameItem tem tags internas, buscamos o botão pelo content description ou tag
        composeTestRule.onNodeWithTag("favorite_button").performClick()
        
        assert(clickedId == gameId)
    }
}
