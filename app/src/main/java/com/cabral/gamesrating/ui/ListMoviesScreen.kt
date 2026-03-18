package com.cabral.gamesrating.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabral.gamesrating.ui.theme.GamesRatingTheme// Caso use TopAppBar depois

@Composable
fun ListMoviesScreen(modifier: Modifier = Modifier) { // Adicionei um default aqui
    GamesRatingTheme {
        Scaffold { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column(modifier = modifier.padding(horizontal = 10.dp)) {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                        item {
//                            MovieItem()
//                        }
//                        item {
//                            MovieItem()
//                        }
//                        item {
//                            MovieItem()
//                        }
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun ListMoviesScreenPreview() {
    ListMoviesScreen(modifier = Modifier)
}