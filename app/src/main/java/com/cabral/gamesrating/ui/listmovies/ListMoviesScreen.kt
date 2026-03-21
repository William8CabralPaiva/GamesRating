package com.cabral.gamesrating.ui.listmovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ListMoviesScreen(
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    listMoviesViewModel: ListMoviesViewModel = hiltViewModel(),
) {
    val games = listMoviesViewModel.games.collectAsLazyPagingItems()

    ListMoviesContent(games = games, modifier = modifier, onClick = onClick)
}

@Composable
fun ListMoviesContent(
    games: LazyPagingItems<GameUi>,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
) {
    when {
        games.loadState.refresh is LoadState.Loading -> {
            ListMoviesLoaded(games = null, modifier = modifier, onClick)
        }

        games.loadState.refresh is LoadState.Error -> {
            ListMoviesError(modifier = modifier)
        }

        games.itemCount == 0 -> {
            ListMoviesEmpty(modifier = modifier)
        }

        else -> {
            ListMoviesLoaded(games = games, modifier = modifier, onClick)
        }
    }
}

// Função previewável — recebe lista simples ou null (shimmer)
@Composable
fun ListMoviesLoaded(
    games: LazyPagingItems<GameUi>?,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
) {
    GamesRatingTheme {
        Scaffold { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .systemBarsPadding()
            ) {
                Column(modifier = modifier.padding(horizontal = 10.dp)) {
                    if (games == null) {
                        // Loading / shimmer
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(10) { MovieItem(gameUi = null, isLoading = true) }
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            items(count = games.itemCount) { index ->
                                MovieItem(
                                    gameUi = games[index],
                                    isLoading = false,
                                    onClick = onClick
                                )
                            }
                            if (games.loadState.append is LoadState.Loading) {
                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListMoviesError(modifier: Modifier = Modifier) {
    GamesRatingTheme {
        Scaffold { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Text(
                    text = stringResource(R.string.error_list),
                    modifier = modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ListMoviesEmpty(modifier: Modifier = Modifier) {
    GamesRatingTheme {
        Scaffold { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Text(
                    text = stringResource(R.string.empty_list),
                    modifier = modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Success State")
@Composable
fun ListMoviesSuccessPreview() {
    val fakeGames = listOf(
        GameUi(
            1,
            name = "The Witcher 3",
            emptyList(),
            "2015-05-19",
            "",
            4.9,
            emptyList(),
            4.9,
            emptyList(),
            "Ação, aventura"
        ),
        GameUi(
            2,
            name = "Elden Ring",
            emptyList(),
            "2015-05-19",
            "",
            4.9,
            emptyList(),
            4.9,
            emptyList(),
            "Ação, aventura"
        ),
    )

    // Cria um PagingData estático só para preview
    val pagingData = PagingData.from(fakeGames)
    val flow = flowOf(pagingData)
    val lazyItems = flow.collectAsLazyPagingItems()

    ListMoviesLoaded(games = lazyItems)
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun ListMoviesLoadingPreview() {
    ListMoviesLoaded(games = null)  // null = shimmer
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun ListMoviesErrorPreview() {
    ListMoviesError()
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun ListMoviesEmptyPreview() {
    ListMoviesEmpty()
}