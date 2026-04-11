package com.cabral.gamesrating.ui.model

import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.data.model.toGenreString

data class GameUi(
    val id: Int,
    val name: String,
    val released: String?,
    val backgroundImage: String?,
    val rating: Double,
    val genres: String?,
    var isFavorite: Boolean,
    val orderId: Int? = null,
)

fun Game.toGameUi(isFavorite: Boolean): GameUi {
    return GameUi(
        id, name, released, background_image, rating, genres.toGenreString(), isFavorite
    )
}
