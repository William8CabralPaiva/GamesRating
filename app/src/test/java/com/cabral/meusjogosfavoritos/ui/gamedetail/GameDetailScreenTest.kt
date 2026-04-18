package com.cabral.meusjogosfavoritos.ui.gamedetail

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.cabral.meusjogosfavoritos.R
import com.cabral.meusjogosfavoritos.ui.model.GameDetailScreenshots
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class GameDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_show_loading_state_when_uiState_is_loading() {
        // Given
        val uiState = GamesUiState.Loading

        // When
        composeTestRule.setContent {
            GameDetailContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("loading_state").assertIsDisplayed()
    }

    @Test
    fun should_show_error_message_when_uiState_is_error() {
        // Given
        val errorMessage = "Erro ao carregar detalhes"
        val uiState = GamesUiState.Error(errorMessage)

        // When
        composeTestRule.setContent {
            GameDetailContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("error_state").assertIsDisplayed()
        composeTestRule.onNodeWithTag("error_message")
            .assertTextEquals(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_game_details_when_uiState_is_success() {
        // Given
        val mockGame = GameDetailScreenshots(
            id = 1,
            name = "The Witcher 3",
            description = "RPG de ação",
            rating = 4.8,
            genres = listOf(R.string.genre_rpg),
            platforms = "PC",
            released = "2015-05-19",
            backgroundImage = "url_principal",
            screenshots = listOf("url1", "url2")
        )
        val uiState = GamesUiState.Success(mockGame)

        // When
        composeTestRule.setContent {
            GameDetailContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("game_detail_success").assertIsDisplayed()
        composeTestRule.onNodeWithTag("game_name").assertTextEquals("The Witcher 3")
        composeTestRule.onNodeWithTag("game_released").assertTextEquals("2015-05-19")
        composeTestRule.onNodeWithTag("rating_surface").assertIsDisplayed()
    }

    @Test
    fun should_display_screenshots_list_when_available() {
        // Given
        val mockGame = GameDetailScreenshots(
            id = 1,
            name = "God of War",
            description = "Ação",
            rating = 5.0,
            genres = listOf(R.string.genre_action),
            platforms = "PS5",
            released = "2022-11-09",
            backgroundImage = "url_principal",
            screenshots = listOf("url1", "url2", "url3")
        )
        val uiState = GamesUiState.Success(mockGame)

        // When
        composeTestRule.setContent {
            GameDetailContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("screenshots_row").assertIsDisplayed()
        // Verifica se existem 3 itens de screenshot na lista
        composeTestRule.onAllNodesWithTag("screenshot_item").assertCountEquals(3)
    }

    @Test
    fun should_change_selected_image_when_clicking_on_screenshot() {
        // Given
        val mainImage = "url_principal"
        val screenshotToClick = "url_clicada"
        val mockGame = GameDetailScreenshots(
            id = 1,
            name = "Elden Ring",
            description = "Soulslike",
            rating = 4.9,
            genres = listOf(R.string.genre_rpg),
            platforms = "PC",
            released = "2022-02-25",
            backgroundImage = mainImage,
            screenshots = listOf(screenshotToClick)
        )
        val uiState = GamesUiState.Success(mockGame)

        composeTestRule.setContent {
            GameDetailContent(uiState = uiState)
        }

        // When
        // Clica na primeira screenshot da lista
        composeTestRule.onAllNodesWithTag("screenshot_item")[0].performClick()

        // Then
        composeTestRule.onNodeWithTag("selected_image").assertExists()
    }

    @Test
    fun should_show_download_button_in_success_state() {
        // Given
        val mockGame = GameDetailScreenshots(
            id = 1,
            name = "Cyberpunk 2077",
            description = "",
            rating = 4.0,
            genres = emptyList(),
            platforms = "",
            released = "",
            backgroundImage = "url",
            screenshots = emptyList()
        )
        val uiState = GamesUiState.Success(mockGame)

        // When
        composeTestRule.setContent {
            GameDetailContent(uiState = uiState)
        }

        // Then
        composeTestRule.onNodeWithTag("download_button")
            .assertIsDisplayed()
            .assertHasClickAction()
    }
}
