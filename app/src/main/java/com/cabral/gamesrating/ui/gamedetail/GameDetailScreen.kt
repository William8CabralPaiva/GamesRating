package com.cabral.gamesrating.ui.gamedetail

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cabral.gamesrating.R
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

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        gameDetailViewmodel.eventFlow.collect {
            showToast(context, it)
        }
    }

    GameDetailContent(
        uiState = uiState,
        modifier = modifier,
        onDownloadClick = { url, name ->
            gameDetailViewmodel.downloadImage(url, name)
        }
    )
}

@Composable
fun GameDetailContent(
    uiState: GamesUiState,
    modifier: Modifier = Modifier,
    onDownloadClick: (String, String) -> Unit = { _, _ -> },
) {
    Box(modifier = modifier.testTag("game_detail_content")) {
        when (uiState) {
            is GamesUiState.Loading -> GameDetailLoading(modifier)
            is GamesUiState.Success -> GameDetailSuccess(
                game = uiState.game,
                modifier = modifier,
                onDownloadClick = onDownloadClick
            )

            is GamesUiState.Error -> GameDetailError(uiState.message, modifier)
        }
    }
}

@Composable
fun GameDetailLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .testTag("loading_state")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shimmer()
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            userScrollEnabled = false
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .shimmer()
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(24.dp)
                    .shimmer()
            )
            Spacer(modifier = Modifier.height(12.dp))
            repeat(3) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(16.dp)
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun GameDetailSuccess(
    game: GameDetailScreenshots,
    modifier: Modifier = Modifier,
    onDownloadClick: (String, String) -> Unit,
) {
    var selectedImage by remember { mutableStateOf(game.backgroundImage) }

    // Resolve os gêneros para strings usando o contexto do Compose
    val genresString = game.genres?.map { stringResource(id = it) }?.joinToString(", ") ?: ""

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .testTag("game_detail_success")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Crossfade(
                targetState = selectedImage,
                animationSpec = tween(durationMillis = 500),
                label = "GameImageCrossfade"
            ) { targetImage ->
                AsyncImage(
                    model = targetImage,
                    contentDescription = "Imagem selecionada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .testTag("selected_image")
                )
            }

            IconButton(
                onClick = {
                    selectedImage?.let { url ->
                        onDownloadClick(url, game.name)
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .testTag("download_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Baixar Imagem",
                    tint = Color.White
                )
            }

            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .testTag("rating_surface")
            ) {
                RatingStar(
                    rating = game.rating ?: 0.0,
                    isLoading = false,
                    textColor = Color.White,
                    layout = RatingLayout.Horizontal,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        if (!game.screenshots.isNullOrEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.testTag("screenshots_row")
            ) {
                items(game.screenshots) { screenshot ->
                    AsyncImage(
                        model = screenshot,
                        contentDescription = "Screenshot",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .clickable {
                                selectedImage = screenshot
                            }
                            .testTag("screenshot_item"),
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = game.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("game_name")
            )
            Spacer(modifier = Modifier.height(8.dp))

            game.platforms?.let {
                Text(text = it, modifier = Modifier.testTag("game_platforms"))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = genresString, modifier = Modifier.testTag("game_genres"))

            Spacer(modifier = Modifier.height(8.dp))

            game.released?.let {
                Text(text = it, modifier = Modifier.testTag("game_released"))
            }

            Spacer(modifier = Modifier.height(8.dp))

            game.description?.let {
                Box(modifier = Modifier.testTag("game_description")) {
                    ExpandableHtmlText(AnnotatedString.fromHtml(it))
                }
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
        modifier = modifier
            .fillMaxSize()
            .testTag("error_state"),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, modifier = Modifier.testTag("error_message"))
    }
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun GameDetailLoadingPreview() {
    GameDetailLoading()
}

@Preview(showBackground = true)
@Composable
fun GameDetailErrorPreview() {
    GameDetailError(message = "Erro ao carregar jogo 😢")
}

@Preview(showBackground = true)
@Composable
fun GameDetailContentSuccessPreview() {
    val mockGame = GameDetailScreenshots(
        id = 1,
        name = "The Witcher 3",
        description = "<p>Um RPG incrível com mundo aberto.</p>",
        rating = 4.8,
        genres = listOf(R.string.genre_action, R.string.genre_rpg),
        platforms = "PC, PS4",
        released = "2015-05-19",
        backgroundImage = "",
        screenshots = listOf("url1", "url2")
    )

    GameDetailContent(
        uiState = GamesUiState.Success(mockGame),
        onDownloadClick = { _, _ -> }
    )
}
