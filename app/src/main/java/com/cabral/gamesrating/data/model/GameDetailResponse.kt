package com.cabral.gamesrating.data.model

data class GameDetailResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val platforms: List<PlatformWrapper>?,
    val genres: List<Genre>?,
    val released: String?,
    val rating: Double?,
    val background_image: String?,
    val background_image_additional: String?,
)
