package com.cabral.gamesrating.domain.model

import com.cabral.gamesrating.data.model.Game

data class GamesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Game>?
)