package com.cabral.meusjogosfavoritos.ui.listgamesfavorites

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.cabral.meusjogosfavoritos.R
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ListGamesFavoriteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

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
        val errorText = context.getString(R.string.error_list)

        // When
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("error_state").assertIsDisplayed()
        composeTestRule.onNodeWithText(errorText).assertIsDisplayed()
    }

    @Test
    fun should_show_empty_state_when_list_is_empty() {
        // Given
        val uiState = FavoritesGamesUiState.Success(emptyList())
        val emptyText = context.getString(R.string.add_favorite_itens)

        // When
        composeTestRule.setContent {
            ListGameFavoriteContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
        composeTestRule.onNodeWithText(emptyText).assertIsDisplayed()
    }

    @Test
    fun should_display_favorites_list_when_uiState_is_success() {
        // Given
        val gameName = "Elden Ring"
        val fakeGames = listOf(
            GameUi(1, gameName, "2022-02-25", "", 4.9, listOf(R.string.genre_action), true)
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
            GameUi(gameId, "God of War", "", "", 5.0, emptyList(), true)
        )
        val uiState = FavoritesGamesUiState.Success(fakeGames)

        composeTestRule.setContent {
            ListGameFavoriteContent(
                uiState = uiState,
                onClickFavorite = { capturedId = it }
            )
        }

        // When
        // Usamos o testTag que definimos no IconButton do GameItem
        composeTestRule.onNodeWithTag("favorite_button")
            .performClick()

        // Then
        assert(capturedId == gameId)
    }
}
