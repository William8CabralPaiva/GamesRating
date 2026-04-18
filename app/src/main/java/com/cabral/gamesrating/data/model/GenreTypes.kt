package com.cabral.gamesrating.data.model

import com.cabral.gamesrating.R

enum class GenreTypes(
    val slug: String,
    val nameRes: Int
) {
    ACTION("action", R.string.genre_action),
    INDIE("indie", R.string.genre_indie),
    ADVENTURE("adventure", R.string.genre_adventure),
    RPG("role-playing-games-rpg", R.string.genre_rpg),
    STRATEGY("strategy", R.string.genre_strategy),
    SHOOTER("shooter", R.string.genre_shooter),
    CASUAL("casual", R.string.genre_casual),
    SIMULATION("simulation", R.string.genre_simulation),
    PUZZLE("puzzle", R.string.genre_puzzle),
    ARCADE("arcade", R.string.genre_arcade),
    PLATFORMER("platformer", R.string.genre_platformer),
    MASSIVELY_MULTIPLAYER("massively-multiplayer", R.string.genre_multiplayer),
    RACING("racing", R.string.genre_racing),
    SPORTS("sports", R.string.genre_sports),
    FIGHTING("fighting", R.string.genre_fighting),
    FAMILY("family", R.string.genre_family),
    BOARD_GAMES("board-games", R.string.genre_board_games),
    CARD("card", R.string.genre_card),
    EDUCATIONAL("educational", R.string.genre_educational),
    UNKNOWN("unknown", R.string.genre_unknown);

    companion object {
        fun fromSlug(slug: String?): GenreTypes {
            if (slug.isNullOrBlank()) return UNKNOWN

            return entries.firstOrNull { it.slug == slug }
                ?: UNKNOWN
        }
    }
}
