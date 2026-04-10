package com.cabral.gamesrating.ui.listgames

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.cabral.gamesrating.ui.model.GameUi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ListGamesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Helper para criar PagingData com estados controlados
    private fun createPagingData(
        items: List<GameUi> = emptyList(),
        refresh: LoadState = LoadState.NotLoading(false)
    ) = flowOf(
        PagingData.from(
            items,
            sourceLoadStates = LoadStates(
                refresh = refresh,
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            )
        )
    )

    @Test
    fun stateLoading_shouldShowLoadingState() {
        composeTestRule.setContent {
            val loadingPagingData = flowOf(
                PagingData.empty<GameUi>(
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.Loading,
                        prepend = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false)
                    )
                )
            ).collectAsLazyPagingItems()

            ListGamesContent(
                games = loadingPagingData,
                searchText = "",
                onSearchChange = {}
            )
        }

        composeTestRule.onNodeWithTag("loading_state").assertIsDisplayed()
    }

    @Test
    fun stateSuccess_shouldShowListWithItems() {
        val gameName = "Elden Ring"
        
        composeTestRule.setContent {
            val games = createPagingData(
                items = listOf(GameUi(1, gameName, "2022", "", 4.9, "Ação", false))
            ).collectAsLazyPagingItems()

            ListGamesContent(
                games = games,
                searchText = "",
                onSearchChange = {}
            )
        }

        // Aguarda a UI processar o PagingData
        composeTestRule.onNodeWithTag("games_list").assertIsDisplayed()
        composeTestRule.onNodeWithText(gameName).assertIsDisplayed()
    }

    @Test
    fun stateEmpty_shouldShowEmptyMessage() {
        composeTestRule.setContent {
            val emptyGames = createPagingData(items = emptyList()).collectAsLazyPagingItems()

            ListGamesContent(
                games = emptyGames,
                searchText = "",
                onSearchChange = {}
            )
        }

        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun stateError_shouldShowErrorMessage() {
        composeTestRule.setContent {
            val errorPagingData = flowOf(
                PagingData.empty<GameUi>(
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.Error(Exception("Erro")),
                        prepend = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false)
                    )
                )
            ).collectAsLazyPagingItems()

            ListGamesContent(
                games = errorPagingData,
                searchText = "",
                onSearchChange = {}
            )
        }

        composeTestRule.onNodeWithTag("error_state").assertIsDisplayed()
    }

    @Test
    fun typingInSearchField_shouldTriggerOnSearchChange() {
        var typedText = ""
        
        composeTestRule.setContent {
            // Importante: O campo só habilita se refresh for NotLoading
            val games = createPagingData().collectAsLazyPagingItems()
            
            ListGamesContent(
                games = games,
                searchText = "",
                onSearchChange = { typedText = it }
            )
        }

        composeTestRule.onNodeWithTag("search_field")
            .assertIsEnabled()
            .performTextInput("Zelda")
        
        assert(typedText == "Zelda")
    }
}
