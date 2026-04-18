package com.cabral.gamesrating.ui.listgamesfavorites

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
        onClickFavorite = { id -> listGamesViewModel.removeFavorite(id) },
        onMove = { from, to -> listGamesViewModel.onMove(from, to) },
        onDragEnd = { listGamesViewModel.onDragEnd() }
    )
}

@Composable
fun ListGameFavoriteContent(
    uiState: FavoritesGamesUiState,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (id: Int) -> Unit = {},
    onMove: (Int, Int) -> Unit = { _, _ -> },
    onDragEnd: () -> Unit = {}
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
                        onMove = onMove,
                        onDragEnd = onDragEnd,
                        modifier = Modifier.testTag("favorite_list")
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGameFavoriteLoaded(
    games: List<GameUi>?,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (id: Int) -> Unit = {},
    onMove: (Int, Int) -> Unit = { _, _ -> },
    onDragEnd: () -> Unit = {}
) {
    if (games == null) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
        ) {
            items(10) {
                GameItem(gameUi = null, isLoading = true)
            }
        }
    } else {
        val listState = rememberLazyListState()
        var draggedItemIndex by remember { mutableIntStateOf(-1) }
        var draggingOffset by remember { mutableFloatStateOf(0f) }

        LazyColumn(
            state = listState,
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp)
        ) {
            itemsIndexed(
                items = games,
                key = { _, game -> game.id }
            ) { index, game ->
                val isDragging = index == draggedItemIndex

                val scale by animateFloatAsState(
                    targetValue = if (isDragging) 1.05f else 1f,
                    animationSpec = spring(stiffness = 300f),
                    label = "scale"
                )
                val elevation by animateDpAsState(
                    targetValue = if (isDragging) 12.dp else 2.dp,
                    animationSpec = spring(stiffness = 300f),
                    label = "elevation"
                )

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                    modifier = Modifier
                        .zIndex(if (isDragging) 1f else 0f)
                        .graphicsLayer {
                            translationY = if (isDragging) draggingOffset else 0f
                            scaleX = scale
                            scaleY = scale
                        }
                        .animateItem(
                            placementSpec = spring(stiffness = 400f, dampingRatio = 0.8f)
                        )
                        .pointerInput(games) { // Importante: recarregar quando a lista mudar
                            detectDragGesturesAfterLongPress(
                                onDragStart = {
                                    draggedItemIndex = index
                                },
                                onDragEnd = {
                                    draggedItemIndex = -1
                                    draggingOffset = 0f
                                    onDragEnd()
                                },
                                onDragCancel = {
                                    draggedItemIndex = -1
                                    draggingOffset = 0f
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    draggingOffset += dragAmount.y

                                    val layoutInfo = listState.layoutInfo
                                    val currentItem = layoutInfo.visibleItemsInfo
                                        .find { it.index == draggedItemIndex } ?: return@detectDragGesturesAfterLongPress

                                    // Calcula o centro do item que está sendo arrastado
                                    val currentCenter = currentItem.offset + (currentItem.size / 2) + draggingOffset

                                    // Busca o item que está "embaixo" do centro do item arrastado
                                    val targetItem = layoutInfo.visibleItemsInfo
                                        .find { item ->
                                            val itemStart = item.offset
                                            val itemEnd = item.offset + item.size
                                            currentCenter.toInt() in itemStart..itemEnd && item.index != draggedItemIndex
                                        }

                                    if (targetItem != null) {
                                        val targetIndex = targetItem.index
                                        // Notifica a mudança de posição no ViewModel
                                        onMove(draggedItemIndex, targetIndex)

                                        // COMPENSAÇÃO DO PULO:
                                        // Ajustamos o draggingOffset subtraindo a distância que o item percorreu no layout
                                        draggingOffset += (currentItem.offset - targetItem.offset)
                                        draggedItemIndex = targetIndex
                                    }
                                }
                            )
                        }
                ) {
                    GameItem(
                        gameUi = game,
                        isLoading = false,
                        onClick = onClick,
                        onClickFavorite = {
                            it?.id?.let { id -> onClickFavorite(id) }
                        }
                    )
                }
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
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.add_favorite_itens)
        )
    }
}

@Preview(showBackground = true, name = "Success State")
@Composable
fun ListGameFavoriteSuccessPreview() {
    val fakeGames = listOf(
        GameUi(1, "The Witcher 3", "2015-05-19", "", 4.9, listOf(R.string.genre_action, R.string.genre_rpg), false),
        GameUi(2, "Elden Ring", "2022-02-25", "", 4.9, listOf(R.string.genre_action, R.string.genre_rpg), false),
    )

    GamesRatingTheme {
        Surface {
            ListGameFavoriteLoaded(games = fakeGames)
        }
    }
}
