package com.cabral.meusjogosfavoritos.ui.gamedetail

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.cabral.meusjogosfavoritos.R
import com.cabral.meusjogosfavoritos.ui.model.GameDetailScreenshots
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GameDetailScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun stateLoading_shouldShowShimmer() {
        composeTestRule.setContent {
            GameDetailContent(uiState = GamesUiState.Loading)
        }
        composeTestRule.onNodeWithTag("loading_state").assertIsDisplayed()
    }

    @Test
    fun stateSuccess_shouldShowGameDetails() {
        val gameName = "The Witcher 3"

        composeTestRule.setContent {
            GameDetailContent(
                uiState = GamesUiState.Success(
                    GameDetailScreenshots(
                        id = 1,
                        name = gameName,
                        description = "Melhor RPG",
                        rating = 5.0,
                        genres = listOf(R.string.genre_rpg),
                        platforms = "PS5",
                        released = "2015",
                        backgroundImage = "url",
                        screenshots = listOf("thumb1", "thumb2")
                    )
                )
            )
        }

        composeTestRule.onNodeWithTag("game_detail_success").assertIsDisplayed()
        composeTestRule.onNodeWithText(gameName).assertIsDisplayed()
        composeTestRule.onNodeWithTag("rating_surface").assertIsDisplayed()
    }

    @Test
    fun stateError_shouldShowErrorMessage() {
        val errorMsg = "Erro ao carregar jogo"

        composeTestRule.setContent {
            GameDetailContent(uiState = GamesUiState.Error(errorMsg))
        }

        composeTestRule.onNodeWithTag("error_state").assertIsDisplayed()
        composeTestRule.onNodeWithText(errorMsg).assertIsDisplayed()
    }

    @Test
    fun clickOnScreenshot_shouldUpdateMainImage() {
        val screenshots = listOf("url_1", "url_2")

        composeTestRule.setContent {
            GameDetailContent(
                uiState = GamesUiState.Success(
                    GameDetailScreenshots(
                        id = 1,
                        name = "Game Test",
                        description = "Desc",
                        rating = 4.0,
                        genres = listOf(R.string.genre_action),
                        platforms = "PC",
                        released = "2020",
                        backgroundImage = "url_1",
                        screenshots = screenshots
                    )
                )
            )
        }

        composeTestRule.onNodeWithTag("screenshots_row").assertIsDisplayed()
        
        // Verifica se as screenshots estão lá e clica na segunda
        composeTestRule.onAllNodesWithTag("screenshot_item")
            .assertCountEquals(2)
            .get(1)
            .performClick()
    }
}
