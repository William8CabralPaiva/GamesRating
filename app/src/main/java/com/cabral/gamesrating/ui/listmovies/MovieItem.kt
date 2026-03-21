package com.cabral.gamesrating.ui.listmovies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.components.RatingStar
import com.cabral.gamesrating.ui.model.GameUi
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import com.cabral.gamesrating.ui.theme.GreyDarkLight
import com.cabral.gamesrating.utils.shimmer

@Composable()
fun MovieItem(
    gameUi: GameUi?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    GamesRatingTheme {
        Surface {
            Card(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = GreyDarkLight
                ),
                elevation = CardDefaults.cardElevation(20.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = gameUi?.background_image,
                        placeholder = painterResource(R.drawable.loading_image),
                        contentDescription = stringResource(R.string.image_game, gameUi?.name?:""),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .shimmer(isLoading)
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 4.dp, end = 8.dp),
                    ) {
                        Text(
                            text = gameUi?.name?:"",
                            fontSize = 20.sp,
                            fontWeight = Bold,
                            color = Color.Black,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmer(isLoading)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = gameUi?.genres ?: "",
                            fontSize = 14.sp,
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmer(isLoading)
                        )
                    }

                    RatingStar(
                        rating = gameUi?.rating?:0.0,
                        isLoading = isLoading,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    val gameUi = GameUi(
        0,
        name = "Shadow of the colossus",
        platforms = listOf(),
        released = "",
        background_image = "",
        rating = 5.0,
        tags = listOf(),
        score = 0.0,
        short_screenshots = listOf(),
        genres = "Ação, Aventura"
    )
    MovieItem(gameUi, false)
}