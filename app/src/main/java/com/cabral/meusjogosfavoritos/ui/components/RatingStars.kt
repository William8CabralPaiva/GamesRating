package com.cabral.meusjogosfavoritos.ui.components

import androidx.compose.foundation.layout.*
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
import com.cabral.meusjogosfavoritos.ui.theme.GamesRatingTheme
import com.cabral.meusjogosfavoritos.utils.shimmer

enum class RatingLayout {
    Vertical,
    Horizontal
}

@Composable
fun RatingStar(
    rating: Double?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    layout: RatingLayout = RatingLayout.Vertical,
    textColor: Color = Color.Black,
) {

    val icon = when {
        rating != null && rating >= 4.0 -> Icons.Default.Star
        rating != null && rating >= 2.0 -> Icons.AutoMirrored.Filled.StarHalf
        else -> Icons.Default.StarOutline
    }

    if (layout == RatingLayout.Vertical) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            StarContent(icon, rating, isLoading, textColor, isVertical = true)
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            StarContent(icon, rating, isLoading, textColor, isVertical = false)
        }
    }
}

@Composable
private fun StarContent(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    rating: Double?,
    isLoading: Boolean,
    textColor: Color,
    isVertical: Boolean
) {
    Icon(
        imageVector = icon,
        contentDescription = "Rating: $rating",
        tint = Color(0xFFFFC107),
        modifier = Modifier.shimmer(isLoading)
    )

    if (isVertical) {
        Spacer(modifier = Modifier.size(4.dp))
    } else {
        Spacer(modifier = Modifier.width(4.dp))
    }

    Text(
        text = rating?.toString() ?: "-",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodySmall,
        color = textColor,
        modifier = Modifier.shimmer(isLoading)
    )
}

@Preview(showBackground = true)
@Composable
fun RatingStarPreview() {
    GamesRatingTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {

                // ⭐ Casos normais (vertical)
                Row {
                    RatingStar(5.0, false, modifier = Modifier.padding(8.dp))
                    RatingStar(2.5, false, modifier = Modifier.padding(8.dp))
                    RatingStar(0.0, false, modifier = Modifier.padding(8.dp)) // 👈 zero
                }

                // ⭐ Horizontal
                Row {
                    RatingStar(
                        rating = 4.5,
                        isLoading = false,
                        layout = RatingLayout.Horizontal,
                        textColor = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // ⏳ Loading
                Row {
                    RatingStar(
                        rating = 3.5,
                        isLoading = true,
                        modifier = Modifier.padding(8.dp)
                    )

                    RatingStar(
                        rating = 0.0,
                        isLoading = true,
                        layout = RatingLayout.Horizontal,
                        textColor = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}