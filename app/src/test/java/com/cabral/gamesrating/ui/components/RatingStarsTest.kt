package com.cabral.gamesrating.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RatingStarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ratingStar_whenRatingIsHigh_showsFullStar() {
        // Arrange
        val rating = 5.0
        val expectedDescription = "Rating: 5.0"

        // Act
        composeTestRule.setContent {
            RatingStar(rating = rating)
        }

        // Assert
        composeTestRule.onNodeWithText("5.0").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(expectedDescription).assertIsDisplayed()
    }

    @Test
    fun ratingStar_whenRatingIsMedium_showsHalfStar() {
        // Arrange
        val rating = 2.5
        val expectedDescription = "Rating: 2.5"

        // Act
        composeTestRule.setContent {
            RatingStar(rating = rating)
        }

        // Assert
        composeTestRule.onNodeWithText("2.5").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(expectedDescription).assertIsDisplayed()
    }

    @Test
    fun ratingStar_whenRatingIsLow_showsEmptyStar() {
        // Arrange
        val rating = 1.0
        val expectedDescription = "Rating: 1.0"

        // Act
        composeTestRule.setContent {
            RatingStar(rating = rating)
        }

        // Assert
        composeTestRule.onNodeWithText("1.0").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(expectedDescription).assertIsDisplayed()
    }
}