package com.cabral.meusjogosfavoritos.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cabral.meusjogosfavoritos.R
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Helper function to get strings in Unit Tests
    private fun getString(resId: Int): String {
        return RuntimeEnvironment.getApplication().getString(resId)
    }

    @Test
    fun `when screen is loaded then it should display settings title`() {
        // Given
        val isDarkTheme = false

        // When
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = {},
                currentLanguage = "pt",
                onLanguageChange = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText(getString(R.string.settings)).assertIsDisplayed()
    }

    @Test
    fun `given dark theme is false when screen loads then switch should be off`() {
        // Given
        val isDarkTheme = false

        // When
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = {},
                currentLanguage = "pt",
                onLanguageChange = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText(getString(R.string.dark_theme)).assertIsDisplayed()
    }

    @Test
    fun `given dark theme is true when screen loads then switch should be on`() {
        // Given
        val isDarkTheme = true

        // When
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = {},
                currentLanguage = "pt",
                onLanguageChange = {}
            )
        }

        // Then
        composeTestRule.onNodeWithText(getString(R.string.dark_theme)).assertIsDisplayed()
    }

    @Test
    fun `when user clicks on theme switch then onToggleTheme callback is triggered`() {
        // Given
        var capturedValue = false
        val isDarkTheme = false

        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = { newValue ->
                    capturedValue = newValue
                },
                currentLanguage = "pt",
                onLanguageChange = {}
            )
        }

        // When
        composeTestRule.onNodeWithText(getString(R.string.dark_theme)).performClick()

        // Then
        assertEquals(true, capturedValue)
    }
}
