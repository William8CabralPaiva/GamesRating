package com.cabral.gamesrating.ui.listmovies

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabral.gamesrating.ui.model.GameUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
class MovieItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private fun buildGameUi(
        id: Int = 1,
        name: String = "Shadow of the Colossus",
        genres: String = "Ação, Aventura",
        rating: Double = 4.5,
        backgroundImage: String = "",
        isFavorite: Boolean = false,
    ) = GameUi(
        id = id,
        name = name,
        platforms = listOf(),
        released = "",
        backgroundImage = backgroundImage,
        rating = rating,
        tags = listOf(),
        score = 0.0,
        shortScreenshots = listOf(),
        genres = genres,
        isFavorite = isFavorite,
    )

    // ─── Content rendering ────────────────────────────────────────────────────────

    @Test
    fun `given valid gameUi, when rendered, then name is displayed`() {
        // Given
        val game = buildGameUi(name = "Shadow of the Colossus")

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule
            .onNodeWithText("Shadow of the Colossus")
            .assertIsDisplayed()
    }

    @Test
    fun `given valid gameUi, when rendered, then genres are displayed`() {
        // Given
        val game = buildGameUi(genres = "Ação, Aventura")

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule
            .onNodeWithText("Ação, Aventura")
            .assertIsDisplayed()
    }

    @Test
    fun `given null gameUi, when rendered, then no name text is shown`() {
        // Given - gameUi is null

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = null, isLoading = false)
        }

        // Then
        composeTestRule
            .onNodeWithText("Shadow of the Colossus")
            .assertDoesNotExist()
    }

    @Test
    fun `given null gameUi, when rendered, then no genres text is shown`() {
        // Given - gameUi is null

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = null, isLoading = false)
        }

        // Then
        composeTestRule
            .onNodeWithText("Ação, Aventura")
            .assertDoesNotExist()
    }

    // ─── Loading state ────────────────────────────────────────────────────────────

    @Test
    fun `given isLoading true, when rendered, then card is still displayed`() {
        // Given - isLoading = true, gameUi = null

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = null, isLoading = true)
        }

        // Then
        composeTestRule
            .onRoot()
            .assertIsDisplayed()
    }

    @Test
    fun `given isLoading false and valid game, when rendered, then content is visible`() {
        // Given
        val game = buildGameUi()

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule
            .onNodeWithText(game.name)
            .assertIsDisplayed()
    }


    @Test
    fun `given valid gameUi, when favorite button is clicked, then onClickFavorite is called`() {
        // Given
        val game = buildGameUi()
        var favoriteClicked = false

        // When
        composeTestRule.setContent {
            MovieItem(
                gameUi = game,
                isLoading = false,
                onClickFavorite = { favoriteClicked = true },
            )
        }
        composeTestRule
            .onNodeWithContentDescription("Botão para favoritar", substring = true, ignoreCase = true)
            .performClick()

        // Then
        assert(favoriteClicked) { "onClickFavorite should have been called" }
    }

    // ─── Click callbacks ──────────────────────────────────────────────────────────

    @Test
    fun `given valid gameUi, when card is clicked, then onClick is called with correct id`() {
        // Given
        val game = buildGameUi(id = 42)
        var clickedId: Int? = null

        // When
        composeTestRule.setContent {
            MovieItem(
                gameUi = game,
                isLoading = false,
                onClick = { id -> clickedId = id },
            )
        }
        composeTestRule
            .onNodeWithText(game.name)
            .performClick()

        // Then
        assert(clickedId == 42) {
            "Expected onClick to be called with id=42 but got $clickedId"
        }
    }

    @Test
    fun `given null gameUi, when card is clicked, then onClick is NOT called`() {
        // Given
        var wasCalled = false

        // When
        composeTestRule.setContent {
            MovieItem(
                gameUi = null,
                isLoading = false,
                onClick = { wasCalled = true },
            )
        }
        composeTestRule.onRoot().performClick()

        // Then
        assert(!wasCalled) { "onClick should not be called when gameUi is null" }
    }

    // ─── Edge cases ───────────────────────────────────────────────────────────────

    @Test
    fun `given very long game name, when rendered, then component does not crash`() {
        // Given
        val game = buildGameUi(name = "A".repeat(200))

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun `given rating zero, when rendered, then component does not crash`() {
        // Given
        val game = buildGameUi(rating = 0.0)

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun `given rating max value, when rendered, then component does not crash`() {
        // Given
        val game = buildGameUi(rating = 5.0)

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onRoot().assertIsDisplayed()
    }

    @Test
    fun `given empty genres, when rendered, then component does not crash`() {
        // Given
        val game = buildGameUi(genres = "")

        // When
        composeTestRule.setContent {
            MovieItem(gameUi = game, isLoading = false)
        }

        // Then
        composeTestRule.onRoot().assertIsDisplayed()
    }
}