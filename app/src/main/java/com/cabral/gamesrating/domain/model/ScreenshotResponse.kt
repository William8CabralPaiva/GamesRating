package com.cabral.gamesrating.domain.model

import com.cabral.gamesrating.data.model.Screenshot

data class ScreenshotResponse(
    val results: List<Screenshot>,
)