package com.cabral.meusjogosfavoritos.di

import com.cabral.meusjogosfavoritos.data.model.Game
import com.cabral.meusjogosfavoritos.data.model.Genre
import com.cabral.meusjogosfavoritos.data.model.Platform
import com.cabral.meusjogosfavoritos.data.model.PlatformWrapper
import com.cabral.meusjogosfavoritos.data.model.Screenshot
import com.cabral.meusjogosfavoritos.data.remote.GamesApi
import com.cabral.meusjogosfavoritos.domain.model.GameDetailResponse
import com.cabral.meusjogosfavoritos.domain.model.GamesResponse
import com.cabral.meusjogosfavoritos.domain.model.ScreenshotResponse
import retrofit2.Response

class FakeGamesApi : GamesApi {

    override suspend fun getAllGames(
        key: String,
        page: Int,
        pageSize: Int,
        search: String
    ): Response<GamesResponse?> {

        val fakeGamesResponse = GamesResponse(
            count = 3,
            next = null,
            previous = null,
            results = listOf(
                Game(
                    id = 58781,
                    slug = "the-elder-scrolls-vi",
                    name = "The Elder Scrolls VI",
                    released = null,
                    background_image = "https://media.rawg.io/media/games/b40/b40eba32d8715d5fdf9634939fe0eca3.jpg",
                    rating = 4.86,
                    platforms = listOf(
                        PlatformWrapper(
                            platform = Platform(
                                id = 4,
                                name = "PC",
                                slug = "pc"
                            )
                        ),
                        PlatformWrapper(
                            platform = Platform(
                                id = 186,
                                name = "Xbox Series S/X",
                                slug = "xbox-series-x"
                            )
                        )
                    ),
                    short_screenshots = listOf(
                        Screenshot(
                            id = -1,
                            image = "https://media.rawg.io/media/games/b40/b40eba32d8715d5fdf9634939fe0eca3.jpg"
                        ),
                        Screenshot(
                            id = 778978,
                            image = "https://media.rawg.io/media/screenshots/b73/b73f234a3d953c7e822c2ef5e54c309f.jpg"
                        )
                    ),
                    genres = listOf(
                        Genre(id = 4, name = "Action", slug = "action"),
                        Genre(id = 5, name = "RPG", slug = "role-playing-games-rpg")
                    )
                ),
                Game(
                    id = 1008779,
                    slug = "warhammer-40000-dawn-of-war-definitive-edition",
                    name = "Warhammer 40,000: Dawn of War - Definitive Edition",
                    released = null,
                    background_image = "https://media.rawg.io/media/screenshots/e2a/e2ac6f56e29b1ac87027f8ff6ca27da0.jpg",
                    rating = 4.83,
                    platforms = listOf(
                        PlatformWrapper(
                            platform = Platform(
                                id = 4,
                                name = "PC",
                                slug = "pc"
                            )
                        )
                    ),
                    short_screenshots = listOf(
                        Screenshot(
                            id = -1,
                            image = "https://media.rawg.io/media/screenshots/e2a/e2ac6f56e29b1ac87027f8ff6ca27da0.jpg"
                        )
                    ),
                    genres = emptyList()
                ),
                Game(
                    id = 975381,
                    slug = "no-case-should-remain-unsolved",
                    name = "No Case Should Remain Unsolved",
                    released = "2024-01-17",
                    background_image = "https://media.rawg.io/media/screenshots/766/76669775675948b1eb3f3eb1c1dfc7dc.jpg",
                    rating = 4.83,
                    platforms = listOf(
                        PlatformWrapper(
                            platform = Platform(
                                id = 4,
                                name = "PC",
                                slug = "pc"
                            )
                        )
                    ),
                    short_screenshots = listOf(
                        Screenshot(
                            id = -1,
                            image = "https://media.rawg.io/media/screenshots/766/76669775675948b1eb3f3eb1c1dfc7dc.jpg"
                        )
                    ),
                    genres = listOf(
                        Genre(id = 3, name = "Adventure", slug = "adventure"),
                        Genre(id = 51, name = "Indie", slug = "indie")
                    )
                )
            )
        )

        return Response.success(fakeGamesResponse)
    }

    override suspend fun getGameById(
        id: Int,
        apiKey: String
    ): Response<GameDetailResponse?> {

        if(id!=-1) {
            val fakeGameDetailResponse = GameDetailResponse(
                id = 58781,
                name = "The Elder Scrolls VI",
                description = "Currently in pre-production at Bethesda Game Studios, the acclaimed developers of Skyrim and Fallout 4. The highly-anticipated next chapter in the iconic The Elder Scrolls series.",
                platforms = listOf(
                    PlatformWrapper(
                        platform = Platform(
                            id = 4,
                            name = "PC",
                            slug = "pc"
                        )
                    ),
                    PlatformWrapper(
                        platform = Platform(
                            id = 186,
                            name = "Xbox Series S/X",
                            slug = "xbox-series-x"
                        )
                    )
                ),
                genres = listOf(
                    Genre(
                        id = 4,
                        name = "Action",
                        slug = "action"
                    ),
                    Genre(
                        id = 5,
                        name = "RPG",
                        slug = "role-playing-games-rpg"
                    )
                ),
                released = null,
                rating = 4.86,
                background_image = "https://media.rawg.io/media/games/b40/b40eba32d8715d5fdf9634939fe0eca3.jpg",
                background_image_additional = null
            )

            return Response.success(fakeGameDetailResponse)
        } else {
            return Response.success(null)
        }
    }

    override suspend fun getScreenshots(
        id: Int,
        apiKey: String
    ): Response<ScreenshotResponse?> {

        val fakeScreenshots = ScreenshotResponse(
            results = listOf(
                Screenshot(
                    id = 1,
                    image = "url_screenshot_1"
                ),
                Screenshot(
                    id = 2,
                    image = "url_screenshot_2"
                )
            )
        )

        return Response.success(fakeScreenshots)
    }
}