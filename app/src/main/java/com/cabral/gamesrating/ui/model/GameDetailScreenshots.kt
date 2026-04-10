package com.cabral.gamesrating.ui.model

data class GameDetailScreenshots(
    val id: Int,
    val name: String,
    val description: String?,
    val platforms: String?,
    val genres: String?,
    val released: String?,
    val rating: Double?,
    val backgroundImage: String?,
    val screenshots: List<String>?,
)
