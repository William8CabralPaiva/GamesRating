package com.cabral.gamesrating.ui.listgames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.components.GameItem
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ListGamesScreen(
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    listGameViewModel: ListGameViewModel = hiltViewModel(),
) {
    val games = listGameViewModel.games.collectAsLazyPagingItems()
    val searchText by listGameViewModel.searchQuery.collectAsState()
    val favoriteIds by listGameViewModel.favoriteIds.collectAsState()

    ListGamesContent(
        games = games,
        modifier = modifier,
        onClick = onClick,
        onClickFavorite = { gameUi ->
            val game = gameUi ?: GameUi(0, "", "", "", 0.0, "", false)
            listGameViewModel.toggleFavorite(game.isFavorite, game)
        },
        searchText = searchText,
        onSearchChange = listGameViewModel::updateSearch,
        favoriteIds = favoriteIds,
    )
}

@Composable
fun ListGamesContent(
    games: LazyPagingItems<GameUi>,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (gameUi: GameUi?) -> Unit = {},
    searchText: String,
    onSearchChange: (String) -> Unit,
    favoriteIds: Set<Int> = emptySet(),
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
    ) {

        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchChange,
            enabled = games.loadState.refresh is LoadState.NotLoading,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.search)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            games.loadState.refresh is LoadState.Loading -> {
                ListGamesLoaded(
                    games = null,
                    onClick = onClick,
                )
            }

            games.loadState.refresh is LoadState.Error -> {
                ListGamesError()
            }

            games.itemCount == 0 -> {
                ListGamesEmpty()
            }

            else -> {
                ListGamesLoaded(
                    games = games,
                    onClick = onClick,
                    onClickFavorite = onClickFavorite,
                    favoriteIds = favoriteIds,
                )
            }
        }
    }
}

@Composable
fun ListGamesLoaded(
    games: LazyPagingItems<GameUi>?,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (gameUi: GameUi?) -> Unit = {},
    favoriteIds: Set<Int> = emptySet(),
) {
    if (games == null) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                horizontal = 10.dp,
                vertical = 8.dp
            )
        ) {
            items(10) {
                GameItem(gameUi = null, isLoading = true)
            }
        }
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                horizontal = 10.dp,
                vertical = 8.dp
            )
        ) {
            items(count = games.itemCount) { index ->
                val game = games[index]
                GameItem(
                    gameUi = game?.copy(isFavorite = game.id in favoriteIds),
                    isLoading = false,
                    onClick = onClick,
                    onClickFavorite = { onClickFavorite(it) }
                )
            }

            if (games.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListGamesError(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.error_list),
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun ListGamesEmpty(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.empty_list),
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, name = "Success State")
@Composable
fun ListGamesSuccessPreview() {
    val fakeGames = listOf(
        GameUi(1, name = "The Witcher 3", "2015-05-19", "", 4.9, "Ação, aventura", false),
        GameUi(2, name = "Elden Ring", "2022-02-25", "", 4.9, "Ação, aventura", false),
    )

    val pagingData = PagingData.from(fakeGames)
    val flow = flowOf(pagingData)
    val lazyItems = flow.collectAsLazyPagingItems()

    GamesRatingTheme {
        Surface {
            ListGamesLoaded(games = lazyItems)
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun ListGamesLoadingPreview() {
    GamesRatingTheme {
        Surface {
            ListGamesLoaded(games = null)
        }
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun ListGamesErrorPreview() {
    GamesRatingTheme {
        Surface {
            ListGamesError()
        }
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun ListGamesEmptyPreview() {
    GamesRatingTheme {
        Surface {
            ListGamesEmpty()
        }
    }
}