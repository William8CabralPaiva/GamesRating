package com.cabral.gamesrating.di

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConvertersTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var converters: Converters

    @Before
    fun setup() {
        converters = Converters()
    }

    @Test
    fun `fromStringList should return json string when list is provided`() {
        // GIVEN
        val list = listOf("Action", "Adventure", "RPG")
        val expectedJson = "[\"Action\",\"Adventure\",\"RPG\"]"

        // WHEN
        val result = converters.fromStringList(list)

        // THEN
        assertEquals(expectedJson, result)
    }

    @Test
    fun `fromStringList should return null when list is null`() {
        // GIVEN
        val list: List<String>? = null

        // WHEN
        val result = converters.fromStringList(list)

        // THEN
        assertEquals("null", result)
        // Nota: O Gson().toJson(null) retorna a string "null".
        // Se quiser que retorne nulo de fato, precisaria de um check manual no seu código.
    }

    @Test
    fun `toStringList should return list of strings when valid json is provided`() {
        // GIVEN
        val json = "[\"Action\",\"Adventure\"]"
        val expectedList = listOf("Action", "Adventure")

        // WHEN
        val result = converters.toStringList(json)

        // THEN
        assertEquals(expectedList, result)
        assertEquals(2, result?.size)
    }

    @Test
    fun `toStringList should return null when string is null`() {
        // GIVEN
        val json: String? = null

        // WHEN
        val result = converters.toStringList(json)

        // THEN
        assertNull(result)
    }

    @Test
    fun `conversion should be reversible`() {
        // GIVEN
        val originalList = listOf("Retro", "Platformer")

        // WHEN
        val json = converters.fromStringList(originalList)
        val resultList = converters.toStringList(json)

        // THEN
        assertEquals(originalList, resultList)
    }
}