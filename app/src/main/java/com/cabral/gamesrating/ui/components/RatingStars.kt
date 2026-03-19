package com.cabral.gamesrating.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabral.gamesrating.ui.theme.GamesRatingTheme
import com.cabral.gamesrating.utils.shimmer

@Composable
fun RatingStar(
    rating: Double?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {

    val icon = when {
        rating != null && rating >= 4.0 -> Icons.Default.Star // Quase cheia ou cheia
        rating != null && rating >= 2.0 -> Icons.AutoMirrored.Filled.StarHalf // Entre 0.3 e 0.7 usamos meia estrela
        else -> Icons.Default.StarOutline // Menos que 0.3 fica vazia
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Rating: $rating",
            tint = Color(0xFFFFC107),
            modifier = Modifier.shimmer(isLoading)
        )

        Text(
            text = rating.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.shimmer(isLoading)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RatingStarPreview() {
    GamesRatingTheme {
        Surface {
            Row(modifier = Modifier.padding(16.dp)) {
                RatingStar(rating = 5.0, false, modifier = Modifier.padding(8.dp)) // Cheia
                RatingStar(rating = 2.5, false, modifier = Modifier.padding(8.dp)) // Metade
                RatingStar(rating = 0.1, false, modifier = Modifier.padding(8.dp)) // Vazia
                RatingStar(rating = 0.1, true, modifier = Modifier.padding(8.dp)) // Vazia
            }
        }
    }
}