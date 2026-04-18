package com.cabral.gamesrating.ui.components

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class LanguageSelectorTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun languageSelector_initialState_pt_selected() {
        composeTestRule.setContent {
            GamesRatingTheme {
                LanguageSelector(
                    currentLanguage = "pt",
                    onLanguageChange = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("language_option_pt").assertIsSelected()
    }

    @Test
    fun languageSelector_initialState_en_selected() {
        composeTestRule.setContent {
            GamesRatingTheme {
                LanguageSelector(
                    currentLanguage = "en",
                    onLanguageChange = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("language_option_en").assertIsSelected()
    }

    @Test
    fun languageSelector_clickOption_callsOnLanguageChange() {
        var selectedLanguage = ""
        composeTestRule.setContent {
            GamesRatingTheme {
                LanguageSelector(
                    currentLanguage = "pt",
                    onLanguageChange = { selectedLanguage = it }
                )
            }
        }

        composeTestRule.onNodeWithTag("language_option_en").performClick()

        assert(selectedLanguage == "en")
    }
}
