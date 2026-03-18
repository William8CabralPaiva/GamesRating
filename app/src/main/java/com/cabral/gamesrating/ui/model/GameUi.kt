package com.cabral.gamesrating.ui.model

import com.cabral.gamesrating.data.model.Game
import com.cabral.gamesrating.data.model.PlatformWrapper
import com.cabral.gamesrating.data.model.Screenshot
import com.cabral.gamesrating.data.model.Tag
import com.cabral.gamesrating.data.model.toGenreString

data class GameUi(
    val id: Int,
    val name: String,
    val platforms: List<PlatformWrapper>,
    val released: String?,
    val background_image: String?,
    val rating: Double,
    val tags: List<Tag>,
    val score: Double?,
    val short_screenshots: List<Screenshot>,
    val genres: String?,
)

fun Game.toGameUi(): GameUi {
    return GameUi(
        id,
        name,
        platforms,
        released,
        background_image,
        rating,
        tags,
        score,
        short_screenshots,
        genres.toGenreString()
    )
}

