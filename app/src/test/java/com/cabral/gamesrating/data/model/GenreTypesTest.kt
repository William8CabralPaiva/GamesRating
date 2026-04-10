package com.cabral.gamesrating.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class GenreTypesTest {

    @Test
    fun `fromSlug should return correct genre when slug is valid`() {
        // given
        val slug = "action"

        // when
        val result = GenreTypes.fromSlug(slug)

        // then
        assertEquals(GenreTypes.ACTION, result)
    }

    @Test
    fun `fromSlug should return UNKNOWN when slug is null`() {
        // given
        val slug: String? = null

        // when
        val result = GenreTypes.fromSlug(slug)

        // then
        assertEquals(GenreTypes.UNKNOWN, result)
    }

    @Test
    fun `fromSlug should return UNKNOWN when slug is blank`() {
        // given
        val slug = "   "

        // when
        val result = GenreTypes.fromSlug(slug)

        // then
        assertEquals(GenreTypes.UNKNOWN, result)
    }

    @Test
    fun `fromSlug should return UNKNOWN when slug does not exist`() {
        // given
        val slug = "invalid-slug"

        // when
        val result = GenreTypes.fromSlug(slug)

        // then
        assertEquals(GenreTypes.UNKNOWN, result)
    }

    @Test
    fun `fromSlug should return correct genre for complex slug`() {
        // given
        val slug = "role-playing-games-rpg"

        // when
        val result = GenreTypes.fromSlug(slug)

        // then
        assertEquals(GenreTypes.RPG, result)
    }

    @Test
    fun `all enum entries should map correctly using their own slug`() {
        // given / when / then
        GenreTypes.entries.forEach { genre ->
            val result = GenreTypes.fromSlug(genre.slug)
            assertEquals(genre, result)
        }
    }
}