package com.cabral.gamesrating.ui.gamedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cabral.gamesrating.ui.components.ExpandableHtmlText
import com.cabral.gamesrating.ui.components.RatingLayout
import com.cabral.gamesrating.ui.components.RatingStar
import com.cabral.gamesrating.ui.model.GameDetailScreenshots
import com.cabral.gamesrating.utils.shimmer

@Composable
fun GameDetailScreen(
    modifier: Modifier = Modifier,
    gameDetailViewmodel: GameDetailViewModel = hiltViewModel(),
) {
    val uiState by gameDetailViewmodel.uiState.collectAsStateWithLifecycle()

    GameDetailContent(uiState = uiState, modifier = modifier)
}


@Composable
fun GameDetailContent(
    uiState: GamesUiState,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is GamesUiState.Loading -> GameDetailLoading(modifier)
        is GamesUiState.Success -> GameDetailSuccess(uiState.game, modifier)
        is GamesUiState.Error -> GameDetailError(uiState.message, modifier)
    }
}

@Composable
fun GameDetailLoading(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmer()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(5) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .shimmer()
                )
            }
        }
    }
}

@Composable
fun GameDetailSuccess(
    game: GameDetailScreenshots,
    modifier: Modifier = Modifier,
) {

    var selectedImage by remember { mutableStateOf(game.backgroundImage) }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = selectedImage,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                RatingStar(
                    rating = game.rating,
                    isLoading = false,
                    textColor = Color.White,
                    layout = RatingLayout.Horizontal,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        if (!game.screenshots.isNullOrEmpty()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                items(game.screenshots) { screenshot ->
                    AsyncImage(
                        model = screenshot,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .clickable{
                                selectedImage = screenshot
                            },
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = game.name, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))

            game.platforms?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            game.genres?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            game.released?.let {
                Text(text = it)
            }

            Spacer(modifier = Modifier.height(8.dp))

            game.description?.let {
                ExpandableHtmlText(AnnotatedString.fromHtml(it))
            }
        }

    }
}

@Composable
fun GameDetailError(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
fun GameDetailLoadingPreview() {
    GameDetailLoading()
}

@Preview(showBackground = true)
@Composable
fun GameDetailErrorPreview() {
    GameDetailError(
        message = "Erro ao carregar jogo 😢"
    )
}

@Preview(showBackground = true)
@Composable
fun GameDetailContentSuccessPreview() {
    val mockGame = GameDetailScreenshots(
        id = 1,
        name = "The Witcher 3",
        description = "<p>Um RPG incrível com mundo aberto.</p>",
        rating = 4.8,
        genres = "Ação, RPG",
        platforms = "PC, PS4",
        released = "2015-05-19",
        backgroundImage = "",
        screenshots = listOf("url1", "url2")
    )

    GameDetailContent(
        uiState = GamesUiState.Success(mockGame)
    )
}