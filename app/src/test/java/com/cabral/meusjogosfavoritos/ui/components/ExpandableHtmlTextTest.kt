package com.cabral.meusjogosfavoritos.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.AnnotatedString
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cabral.meusjogosfavoritos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [34])
class ExpandableHtmlTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // region Helpers

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val shortText = AnnotatedString("Texto curto.")

    private val longText = AnnotatedString(
        "Linha 1\nLinha 2\nLinha 3\nLinha 4\nLinha 5\nLinha 6\nLinha 7\nLinha 8"
    )

    private fun getString(id: Int): String = context.getString(id)

    // endregion

    // region Exibição do texto

    @Test
    fun `given a short text, when component is rendered, then text is displayed`() {
        // Given
        val text = shortText

        // When
        composeTestRule.setContent {
            ExpandableHtmlText(text = text)
        }

        // Then
        composeTestRule
            .onNodeWithText(text.text)
            .assertIsDisplayed()
    }

    @Test
    fun `given a short text without overflow, when component is rendered, then see more button is not displayed`() {
        // Given
        val text = shortText

        // When
        composeTestRule.setContent {
            ExpandableHtmlText(text = text)
        }

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_more))
            .assertDoesNotExist()
    }

    @Test
    fun `given a short text without overflow, when component is rendered, then see less button is not displayed`() {
        // Given
        val text = shortText

        // When
        composeTestRule.setContent {
            ExpandableHtmlText(text = text)
        }

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .assertDoesNotExist()
    }

    // endregion

    // region Estado inicial com overflow

    @Test
    fun `given a long text that overflows, when component is rendered, then see less button is displayed`() {
        // Given
        val text = longText

        // When
        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 2
            )
        }

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .assertIsDisplayed()
    }

    @Test
    fun `given a long text that overflows, when component is rendered, then see more button is not displayed initially`() {
        // Given
        val text = longText

        // When
        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 2
            )
        }

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_more))
            .assertDoesNotExist()
    }

    // endregion

    // region Interação: expandir

    @Test
    fun `given a collapsed long text, when see less button is clicked, then see more button is displayed`() {
        // Given
        val text = longText

        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 2
            )
        }

        // When
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .performClick()

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_more))
            .assertIsDisplayed()
    }

    @Test
    fun `given a collapsed long text, when see less button is clicked, then see less button is no longer displayed`() {
        // Given
        val text = longText

        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 2
            )
        }

        // When
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .performClick()

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .assertDoesNotExist()
    }

    // endregion

    // region Interação: recolher

    @Test
    fun `given an expanded long text, when see more button is clicked, then see less button is displayed again`() {
        // Given
        val text = longText

        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 2
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .performClick()

        // When
        composeTestRule
            .onNodeWithText(getString(R.string.see_more))
            .performClick()

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .assertIsDisplayed()
    }

    @Test
    fun `given an expanded long text, when see more button is clicked, then see more button is no longer displayed`() {
        // Given
        val text = longText

        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 2
            )
        }

        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .performClick()

        // When
        composeTestRule
            .onNodeWithText(getString(R.string.see_more))
            .performClick()

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_more))
            .assertDoesNotExist()
    }

    // endregion

    // region minimizedMaxLines customizado

    @Test
    fun `given minimizedMaxLines set to 1 and long text, when component is rendered, then overflow toggle is displayed`() {
        // Given
        val text = AnnotatedString("Linha A\nLinha B\nLinha C")

        // When
        composeTestRule.setContent {
            ExpandableHtmlText(
                text = text,
                minimizedMaxLines = 1
            )
        }

        // Then
        composeTestRule
            .onNodeWithText(getString(R.string.see_less))
            .assertIsDisplayed()
    }

    // endregion
}