package com.cabral.gamesrating.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.theme.GamesRatingTheme// Caso use TopAppBar depois

@Composable
fun ListMoviesScreen(
    sharedViewModel: GamesSharedViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by sharedViewModel.uiState.collectAsState()

    ListMoviesContent(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun ListMoviesContent(
    uiState: GamesUiState,
    modifier: Modifier = Modifier,
) {
    GamesRatingTheme {
        Scaffold { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column(modifier = modifier.padding(horizontal = 10.dp)) {
                    when (uiState) {
                        is GamesUiState.Loading -> {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                items(10) {
                                    MovieItem(null, true)
                                }
                            }
                        }

                        is GamesUiState.Success -> {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                // Se state.game for uma List<Game>, o import do items(List) deve funcionar
                                items(uiState.game) { game ->
                                    MovieItem(game, false)
                                }
                            }
                        }

                        is GamesUiState.Error -> {
                            Text(text = stringResource(R.string.error_list))
                        }

                        is GamesUiState.Empty -> {
                            Text(text = stringResource(R.string.empty_list))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListMoviesScreenPreview() {
    val fakeState = GamesUiState.Success(game = listOf())

    ListMoviesContent(uiState = fakeState)
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun ListMoviesLoadingPreview() {
    ListMoviesContent(uiState = GamesUiState.Loading)
}