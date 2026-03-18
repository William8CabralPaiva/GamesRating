package com.cabral.gamesrating.data.model

data class Game(
    val id: Int,
    val slug: String,
    val name: String,
    val playtime: Int,
    val platforms: List<PlatformWrapper>,
    val released: String?,
    val background_image: String?,
    val rating: Double,
    val rating_top: Int,
    val ratings: List<Rating>,
    val ratings_count: Int,
    val reviews_text_count: Int,
    val added: Int,
    val metacritic: Int?,
    val score: Double?,
    val tags: List<Tag>,
    val reviews_count: Int,
    val short_screenshots: List<Screenshot>,
    val genres: List<Genre>
)