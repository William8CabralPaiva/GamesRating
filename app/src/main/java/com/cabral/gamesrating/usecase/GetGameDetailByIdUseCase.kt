package com.cabral.gamesrating.usecase

import com.cabral.gamesrating.di.MoviesRepository
import com.cabral.gamesrating.ui.model.GameDetailScreenshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetGameDetailByIdUseCase @Inject constructor(
    private val repository: MoviesRepository,
) {
    operator fun invoke(id: Int): Flow<GameDetailScreenshots> {
        val gameByIdFlow = repository.getGameById(id)
        val screenshotsFlow = repository.getScreenshots(id)

        return combine(gameByIdFlow, screenshotsFlow) { game, screenshots ->

            val screenshotsList = mutableListOf<String>()

            if (!game.background_image.isNullOrEmpty()) {
                screenshotsList.add(game.background_image)
            }

            if (!game.background_image_additional.isNullOrEmpty()) {
                screenshotsList.add(game.background_image_additional)
            }

            val map = screenshots.results.map { it.image }
            screenshotsList.addAll(map)


            GameDetailScreenshots(
                id = game.id,
                name = game.name,
                description = game.description,
                platforms = game.platforms?.joinToString(", ") { it.platform.name },
                genres = game.genres?.joinToString(", ") { it.name },
                released = game.released,
                rating = game.rating,
                backgroundImage = game.background_image,
                screenshots = screenshotsList
            )
        }
    }
}