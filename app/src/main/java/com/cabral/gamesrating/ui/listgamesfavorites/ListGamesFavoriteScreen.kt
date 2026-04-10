package com.cabral.gamesrating.ui.listgamesfavorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.components.GameItem
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.theme.GamesRatingTheme

@Composable
fun ListGamesFavoriteScreen(
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    listGamesViewModel: ListGameFavoriteViewModel = hiltViewModel(),
) {
    val uiState by listGamesViewModel.uiState.collectAsStateWithLifecycle()

    ListGameFavoriteContent(
        uiState = uiState,
        modifier = modifier,
        onClick = onClick,
        onClickFavorite = { id -> listGamesViewModel.removeFavorite(id) }
    )
}

@Composable
fun ListGameFavoriteContent(
    uiState: FavoritesGamesUiState,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (id: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .testTag("list_favorite_content")
    ) {
        when (uiState) {
            is FavoritesGamesUiState.Loading -> {
                ListGameFavoriteLoaded(
                    games = null,
                    onClick = onClick,
                    modifier = Modifier.testTag("loading_state")
                )
            }

            is FavoritesGamesUiState.Error -> {
                ListGameFavoriteError(modifier = Modifier.testTag("error_state"))
            }

            is FavoritesGamesUiState.Success -> {
                if (uiState.listGames.isNullOrEmpty()) {
                    ListGameFavoriteEmpty(modifier = Modifier.testTag("empty_state"))
                } else {
                    ListGameFavoriteLoaded(
                        games = uiState.listGames,
                        onClick = onClick,
                        onClickFavorite = onClickFavorite,
                        modifier = Modifier.testTag("favorite_list")
                    )
                }
            }
        }
    }
}

@Composable
fun ListGameFavoriteLoaded(
    games: List<GameUi>?,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (id: Int) -> Unit = {},
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
            items(
                count = games.size,
                key = { index -> games[index].id }
            ) { index ->
                GameItem(
                    gameUi = games[index],
                    isLoading = false,
                    onClick = onClick,
                    onClickFavorite = {
                        it?.id?.let { id ->
                            onClickFavorite(id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ListGameFavoriteError(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.error_list),
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun ListGameFavoriteEmpty(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.empty_list),
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, name = "Success State")
@Composable
fun ListGameFavoriteSuccessPreview() {
    val fakeGames = listOf(
        GameUi(1, "The Witcher 3", "2015-05-19", "", 4.9, "Ação, aventura", false),
        GameUi(2, "Elden Ring", "2022-02-25", "", 4.9, "Ação, aventura", false),
    )

    GamesRatingTheme {
        Surface {
            ListGameFavoriteLoaded(games = fakeGames)
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun ListGameFavoriteLoadingPreview() {
    GamesRatingTheme {
        Surface {
            ListGameFavoriteLoaded(games = null)
        }
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun ListGameFavoriteErrorPreview() {
    GamesRatingTheme {
        Surface {
            ListGameFavoriteError()
        }
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun ListGameFavoriteEmptyPreview() {
    GamesRatingTheme {
        Surface {
            ListGameFavoriteEmpty()
        }
    }
}