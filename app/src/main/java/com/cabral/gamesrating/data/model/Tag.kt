package com.cabral.gamesrating.data.model

data class Tag(
    val id: Int,
    val name: String,
    val slug: String,
    val language: String,
    val games_count: Int,
    val image_background: String
)