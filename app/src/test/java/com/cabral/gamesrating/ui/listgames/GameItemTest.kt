package com.cabral.gamesrating.ui.listgames

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.components.GameItem
import com.cabral.gamesrating.ui.model.GameUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
class GameItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private fun buildGameUi(
        id: Int = 1,
        name: String = "Shadow of the Colossus",
        genres: List<Int> = listOf(R.string.genre_action, R.string.genre_adventure),
        rating: Double = 4.5,
        isFavorite: Boolean = false,
    ) = GameUi(
        id = id,
        name = name,
        released = "",
        backgroundImage = "https://image.url",
        rating = rating,
        genres = genres,
        isFavorite = isFavorite,
    )

    // ─── Content rendering ────────────────────────────────────────────────────────

    @Test
    fun `given valid gameUi, when rendered, then name and genres are displayed`() {
        // Given
        val game = buildGameUi(name = "God of War", genres = listOf(R.string.genre_action))
        val genreText = context.getString(R.string.genre_action)

        // When
        composeTestRule.setContent {
            GameItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onNodeWithText("God of War").assertIsDisplayed()
        composeTestRule.onNodeWithText(genreText).assertIsDisplayed()
    }

    @Test
    fun `given valid gameUi, when rendered, then image content description is correct`() {
        // Given
        val game = buildGameUi(name = "Elden Ring")
        val expectedDescription = context.getString(R.string.image_game, game.name)

        // When
        composeTestRule.setContent {
            GameItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onNodeWithContentDescription(expectedDescription).assertIsDisplayed()
    }

    // ─── Loading state ────────────────────────────────────────────────────────────

    @Test
    fun `given isLoading true, when rendered, then favorite button is NOT displayed`() {
        // Given
        val game = buildGameUi()
        val favoriteDescription = context.getString(R.string.favorite_button)

        // When
        composeTestRule.setContent {
            GameItem(gameUi = game, isLoading = true)
        }

        // Then
        composeTestRule.onNodeWithContentDescription(favoriteDescription).assertDoesNotExist()
    }

    @Test
    fun `given isLoading false, when rendered, then favorite button IS displayed`() {
        // Given
        val game = buildGameUi()
        val favoriteDescription = context.getString(R.string.favorite_button)

        // When
        composeTestRule.setContent {
            GameItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onNodeWithContentDescription(favoriteDescription).assertIsDisplayed()
    }

    // ─── Click callbacks ──────────────────────────────────────────────────────────

    @Test
    fun `given valid gameUi, when favorite button is clicked, then onClickFavorite is called`() {
        // Given
        val game = buildGameUi(isFavorite = false)
        var favoriteClicked = false
        val favoriteDescription = context.getString(R.string.favorite_button)

        // When
        composeTestRule.setContent {
            GameItem(
                gameUi = game,
                isLoading = false,
                onClickFavorite = { favoriteClicked = true },
            )
        }
        composeTestRule.onNodeWithContentDescription(favoriteDescription).performClick()

        // Then
        assert(favoriteClicked) { "onClickFavorite should have been called" }
        assert(game.isFavorite) { "isFavorite in model should be true after click" }
    }

    @Test
    fun `given valid gameUi, when card is clicked, then onClick is called with correct id`() {
        // Given
        val game = buildGameUi(id = 42)
        var clickedId: Int? = null

        // When
        composeTestRule.setContent {
            GameItem(
                gameUi = game,
                isLoading = false,
                onClick = { id -> clickedId = id },
            )
        }
        composeTestRule.onNodeWithText(game.name).performClick()

        // Then
        assert(clickedId == 42) {
            "Expected onClick to be called with id=42 but got $clickedId"
        }
    }

    // ─── Edge cases ───────────────────────────────────────────────────────────────

    @Test
    fun `given null gameUi, when rendered, then component does not crash`() {
        // Given - gameUi is null

        // When
        composeTestRule.setContent {
            GameItem(gameUi = null, isLoading = false)
        }

        // Then
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun `given null gameUi, when card is clicked, then onClick is NOT called`() {
        // Given
        var wasCalled = false

        // When
        composeTestRule.setContent {
            GameItem(gameUi = null, isLoading = false, onClick = { wasCalled = true })
        }
        composeTestRule.onRoot().performClick()

        // Then
        assert(!wasCalled) { "onClick should not be called when gameUi is null" }
    }
}
