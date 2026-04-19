package com.cabral.meusjogosfavoritos.domain.model

import com.cabral.meusjogosfavoritos.data.model.Game

data class GamesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Game>?
)