package com.cabral.meusjogosfavoritos.ui.settings

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.cabral.meusjogosfavoritos.R
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun should_display_settings_title_when_screen_is_loaded() {
        // Given
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = false,
                onToggleTheme = {},
                currentLanguage = "en",
                onLanguageChange = {}
            )
        }

        // When - The screen is rendered

        // Then
        val settingsTitle = context.getString(R.string.settings)
        composeTestRule.onNodeWithText(settingsTitle).assertIsDisplayed()
    }

    @Test
    fun should_display_dark_mode_option_and_be_off_when_isDarkTheme_is_false() {
        // Given
        val isDarkTheme = false
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = {},
                currentLanguage = "en",
                onLanguageChange = {}
            )
        }

        // When - The screen is rendered with dark theme disabled

        // Then
        val darkThemeText = context.getString(R.string.dark_theme)
        composeTestRule.onNodeWithText(darkThemeText).assertIsDisplayed()
        composeTestRule.onNodeWithText(darkThemeText).assertIsOff()
    }

    @Test
    fun should_display_dark_mode_option_and_be_on_when_isDarkTheme_is_true() {
        // Given
        val isDarkTheme = true
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = {},
                currentLanguage = "en",
                onLanguageChange = {}
            )
        }

        // When - The screen is rendered with dark theme enabled

        // Then
        val darkThemeText = context.getString(R.string.dark_theme)
        composeTestRule.onNodeWithText(darkThemeText).assertIsDisplayed()
        composeTestRule.onNodeWithText(darkThemeText).assertIsOn()
    }

    @Test
    fun should_call_onToggleTheme_with_true_when_dark_mode_is_off_and_clicked() {
        // Given
        var themeToggledValue: Boolean? = null
        composeTestRule.setContent {
            SettingsScreen(
                isDarkTheme = false,
                onToggleTheme = { themeToggledValue = it },
                currentLanguage = "en",
                onLanguageChange = {}
            )
        }

        // When
        val darkThemeText = context.getString(R.string.dark_theme)
        composeTestRule.onNodeWithText(darkThemeText).performClick()

        // Then
        assert(themeToggledValue == true)
    }
}
