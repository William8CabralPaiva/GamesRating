package com.cabral.gamesrating.navigation

sealed class Routes(val route: String) {

    object Game : Routes("game")

    object GameDetail : Routes("gameDetail/{gameId}") {
        fun createRoute(gameId: Int): String {
            return "gameDetail/$gameId"
        }
    }
}