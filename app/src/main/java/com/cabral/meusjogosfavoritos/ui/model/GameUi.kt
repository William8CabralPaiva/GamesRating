package com.cabral.meusjogosfavoritos.ui.model

import com.cabral.meusjogosfavoritos.data.model.Game
import com.cabral.meusjogosfavoritos.data.model.toGenreResList

data class GameUi(
    val id: Int,
    val name: String,
    val released: String?,
    val backgroundImage: String?,
    val rating: Double,
    val genres: List<Int>?,
    var isFavorite: Boolean,
    val orderId: Int? = null,
)

fun Game.toGameUi(isFavorite: Boolean): GameUi {
    return GameUi(
        id, name, released, background_image, rating, genres.toGenreResList(), isFavorite
    )
}
