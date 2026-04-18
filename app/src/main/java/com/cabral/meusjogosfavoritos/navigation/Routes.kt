package com.cabral.meusjogosfavoritos.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.cabral.meusjogosfavoritos.R

sealed class Routes(
    val route: String,
    @param:StringRes val titleId: Int? = null,
    val icon: ImageVector? = null,
) {

    object Game : Routes("game", R.string.games, Icons.Default.SportsEsports)
    object Favorites : Routes("favorites", R.string.favorite, Icons.Default.Star)
    object Settings : Routes("settings", R.string.settings, Icons.Default.Settings)
    object GameDetail : Routes("gameDetail/{gameId}") {
        fun createRoute(gameId: Int): String {
            return "gameDetail/$gameId"
        }
    }
}