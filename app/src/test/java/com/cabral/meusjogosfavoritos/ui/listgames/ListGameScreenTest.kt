package com.cabral.meusjogosfavoritos.ui.listgames

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.cabral.meusjogosfavoritos.ui.model.GameUi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ListGamesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_list_of_games_when_data_is_loaded() {
        // Given
        val gameName = "The Witcher 3"
        val fakeGames = listOf(
            GameUi(1, name = gameName, "2015-05-19", "", 4.9, listOf(R.string.genre_action), false)
        )
        // Forçamos o estado de NotLoading para o campo de busca habilitar
        val gamesFlow = flowOf(
            PagingData.from(
                fakeGames,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.NotLoading(endOfPaginationReached = true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            val lazyItems = gamesFlow.collectAsLazyPagingItems()
            ListGamesContent(
                games = lazyItems,
                searchText = "",
                onSearchChange = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("games_list").assertIsDisplayed()
        composeTestRule.onNodeWithText(gameName).assertIsDisplayed()
    }

    @Test
    fun should_trigger_search_change_when_typing_in_search_field() {
        // Given
        val query = "God of War"
        var capturedQuery = ""
        // Garantimos que o refresh está como NotLoading para o campo habilitar
        val gamesFlow = flowOf(
            PagingData.empty<GameUi>(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(false),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true)
                )
            )
        )

        composeTestRule.setContent {
            val lazyItems = gamesFlow.collectAsLazyPagingItems()
            ListGamesContent(
                games = lazyItems,
                searchText = "",
                onSearchChange = { capturedQuery = it }
            )
        }

        // When
        // Se o campo ainda falhar, usamos waitUnusedToIdle
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("search_field")
            .assertIsEnabled() // Verifica se habilitou
            .performTextInput(query)

        // Then
        assert(capturedQuery == query)
    }

    @Test
    fun should_show_empty_state_when_list_is_empty() {
        // Given
        val gamesFlow = flowOf(
            PagingData.from(
                emptyList<GameUi>(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true)
                )
            )
        )

        // When
        composeTestRule.setContent {
            val lazyItems = gamesFlow.collectAsLazyPagingItems()
            ListGamesContent(
                games = lazyItems,
                searchText = "",
                onSearchChange = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }
}
