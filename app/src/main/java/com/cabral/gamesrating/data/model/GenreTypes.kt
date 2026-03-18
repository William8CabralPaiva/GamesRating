package com.cabral.gamesrating.data.model

enum class GenreTypes(
    val slug: String,
    val englishName: String,
    val portugueseName: String
) {
    ACTION("action", "Action", "Ação"),
    INDIE("indie", "Indie", "Indie"),
    ADVENTURE("adventure", "Adventure", "Aventura"),
    RPG("role-playing-games-rpg", "RPG", "RPG"),
    STRATEGY("strategy", "Strategy", "Estratégia"),
    SHOOTER("shooter", "Shooter", "Tiro"),
    CASUAL("casual", "Casual", "Casual"),
    SIMULATION("simulation", "Simulation", "Simulação"),
    PUZZLE("puzzle", "Puzzle", "Quebra-cabeça"),
    ARCADE("arcade", "Arcade", "Arcade"),
    PLATFORMER("platformer", "Platformer", "Plataforma"),
    MASSIVELY_MULTIPLAYER("massively-multiplayer", "Massively Multiplayer", "Multiplayer Massivo"),
    RACING("racing", "Racing", "Corrida"),
    SPORTS("sports", "Sports", "Esportes"),
    FIGHTING("fighting", "Fighting", "Luta"),
    FAMILY("family", "Family", "Família"),
    BOARD_GAMES("board-games", "Board Games", "Jogos de Tabuleiro"),
    CARD("card", "Card", "Cartas"),
    EDUCATIONAL("educational", "Educational", "Educacional"),
    UNKNOWN("unknown", "Unknown", "Desconhecido");

    companion object {
        fun fromSlug(slug: String?): GenreTypes {
            if (slug.isNullOrBlank()) return UNKNOWN

            return entries.firstOrNull { it.slug == slug }
                ?: UNKNOWN
        }
    }
}