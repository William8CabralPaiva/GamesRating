package com.cabral.gamesrating.data.model

data class Genre(
    val id: Int,
    val name: String,
    val slug: String
)

fun List<Genre>.toGenreResList(): List<Int> {
    return this.map { GenreTypes.fromSlug(it.slug).nameRes }
}
