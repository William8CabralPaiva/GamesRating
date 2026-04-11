package com.cabral.gamesrating.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.theme.BackgroundDark
import com.cabral.gamesrating.ui.theme.GradientDarkEnd
import com.cabral.gamesrating.ui.theme.GradientDarkStart
import com.cabral.gamesrating.ui.theme.GradientLightEnd
import com.cabral.gamesrating.ui.theme.GradientLightStart
import com.cabral.gamesrating.ui.theme.OnSurfaceDark
import com.cabral.gamesrating.ui.theme.SurfaceLight
import com.cabral.gamesrating.ui.theme.Yellow
import com.cabral.gamesrating.utils.shimmer

@Composable
fun GameItem(
    gameUi: GameUi?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onClick: (id: Int) -> Unit = {},
    onClickFavorite: (gameUi: GameUi?) -> Unit = {},
) {

    var isFavoriteState by remember { mutableStateOf(gameUi?.isFavorite ?: false) }

    val isDark = MaterialTheme.colorScheme.background == BackgroundDark

    val gradient = Brush.verticalGradient(
        colors = if (isDark) {
            listOf(GradientDarkStart, GradientDarkEnd)
        } else {
            listOf(GradientLightStart, GradientLightEnd)
        }
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceLight.copy(alpha = 0f)),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = {
            gameUi?.id?.let { onClick(it) }
        }
    ) {
        Row(
            modifier = Modifier
                .background(gradient)
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Box {
                AsyncImage(
                    model = gameUi?.backgroundImage,
                    placeholder = painterResource(R.drawable.loading_image),
                    contentDescription = stringResource(
                        R.string.image_game,
                        gameUi?.name ?: ""
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmer(isLoading)
                )

                if (!isLoading) {
                    IconButton(
                        onClick = {
                            gameUi?.let {
                                isFavoriteState = !isFavoriteState
                                it.isFavorite = isFavoriteState
                                onClickFavorite(gameUi)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .testTag("favorite_button")
                    ) {
                        Icon(
                            imageVector = if (isFavoriteState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite_button),
                            tint = Yellow
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = gameUi?.name ?: "",
                    fontSize = 18.sp,
                    fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.shimmer(isLoading)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = gameUi?.genres ?: "",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.shimmer(isLoading)
                )
            }

            RatingStar(
                rating = gameUi?.rating ?: 0.0,
                isLoading = isLoading,
                textColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .shimmer(isLoading)
            )
        }
    }
}


@Preview
@Composable
fun GameItemPreview() {
    val gameUi = GameUi(
        0,
        name = "Shadow of the colossus",
        released = "",
        backgroundImage = "",
        rating = 5.0,
        genres = "Ação, Aventura",
        false
    )
    GameItem(gameUi, false)
}
