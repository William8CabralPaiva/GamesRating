package com.cabral.gamesrating.data.model

data class Game(
    val id: Int,
    val slug: String,
    val name: String,
    val platforms: List<PlatformWrapper>,
    val released: String?,
    val background_image: String?,
    val rating: Double,
    val short_screenshots: List<Screenshot>,
    val genres: List<Genre>
)