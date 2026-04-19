package com.cabral.meusjogosfavoritos.domain.usecase

import com.cabral.meusjogosfavoritos.data.model.GenreTypes
import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.domain.repository.TranslationRepository
import com.cabral.meusjogosfavoritos.ui.model.GameDetailScreenshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class GetGameDetailByIdUseCase @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val translationRepository: TranslationRepository
) {
    operator fun invoke(id: Int, lang: String): Flow<GameDetailScreenshots> {
        val gameByIdFlow = gamesRepository.getGameById(id)
        val screenshotsFlow = gamesRepository.getScreenshots(id)

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
                genres = game.genres?.map { GenreTypes.fromSlug(it.slug).nameRes },
                released = game.released,
                rating = game.rating,
                backgroundImage = game.background_image,
                screenshots = screenshotsList
            )
        }.map { gameDetail ->
            if (lang != "en") {
                val translatedDescription = gameDetail.description?.let { desc ->
                    val name = gameDetail.name

                    // Substitui o nome do jogo pelo placeholder para evitar tradução indesejada
                    val textToTranslate = desc.replace(name, PLACEHOLDER, ignoreCase = true)
                    val translated = translationRepository.translate(textToTranslate, lang)

                    // Volta o nome original no lugar do placeholder no texto traduzido
                    translated.replace(PLACEHOLDER, name)
                }

                val formattedDate = gameDetail.released?.let { dateStr ->
                    try {
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        // Use Locale.forLanguageTag para evitar o aviso de depreciação
                        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"))
                        val date = inputFormat.parse(dateStr)
                        date?.let { outputFormat.format(it) } ?: dateStr
                    } catch (e: Exception) {
                        dateStr
                    }
                }

                gameDetail.copy(
                    description = translatedDescription,
                    released = formattedDate
                )
            } else {
                gameDetail
            }
        }
    }

    companion object {
        private const val PLACEHOLDER = "_GAME_"
    }
}
