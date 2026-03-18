package com.cabral.gamesrating.data.model

data class Genre(
    val id: Int,
    val name: String,
    val slug: String
)

fun List<Genre>.toGenreString(): String {
    return this.joinToString(", ") { GenreTypes.fromSlug(it.slug).portugueseName  }
}