package com.cabral.gamesrating.ui.gamedetail

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cabral.gamesrating.R
import com.cabral.gamesrating.ui.components.RatingLayout
import com.cabral.gamesrating.ui.components.RatingStar
import com.cabral.gamesrating.ui.theme.GamesRatingTheme

@Composable
fun GameDetailScreen(
    modifier: Modifier = Modifier,
    viewmodel: GameDetailViewModel = hiltViewModel(),
) {
    GameDetailContent()
}

@Composable
fun GameDetailContent(modifier: Modifier = Modifier) {
    GamesRatingTheme {
        Surface {
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
                    Image(
                        painter = painterResource(R.drawable.loading_image),
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
                            rating = 0.0,
                            isLoading = false,
                            textColor = Color.White,
                            layout = RatingLayout.Horizontal,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    item(10) {
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.loading_image),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nome do Jogo (name)", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = AnnotatedString.fromHtml("Descrição do jogo (description)"))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Plataformas (platforms)")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Gêneros (genres)")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Data de Lançamento (released)")
                }

            }

        }
    }
}

@Preview
@Composable
fun GameDetailScreenPreview() {
    GameDetailContent()
}