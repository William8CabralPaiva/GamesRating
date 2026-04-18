package com.cabral.meusjogosfavoritos.navigation


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class RoutesTest {

    @Test
    fun `Game route should be correct`() {
        assertEquals("game", Routes.Game.route)
    }

    @Test
    fun `Favorites route should be correct`() {
        assertEquals("favorites", Routes.Favorites.route)
    }

    @Test
    fun `GameDetail route pattern should be correct`() {
        assertEquals("gameDetail/{gameId}", Routes.GameDetail.route)
    }

    @Test
    fun `createRoute should return correct route with id`() {
        val result = Routes.GameDetail.createRoute(10)

        assertEquals("gameDetail/10", result)
    }

    @Test
    fun `createRoute should work with different ids`() {
        val result1 = Routes.GameDetail.createRoute(1)
        val result2 = Routes.GameDetail.createRoute(999)

        assertEquals("gameDetail/1", result1)
        assertEquals("gameDetail/999", result2)
    }

    @Test
    fun `Game should have title and icon`() {
        assertNotNull(Routes.Game.titleId)
        assertNotNull(Routes.Game.icon)
    }

    @Test
    fun `Favorites should have title and icon`() {
        assertNotNull(Routes.Favorites.titleId)
        assertNotNull(Routes.Favorites.icon)
    }

    @Test
    fun `GameDetail should not have title and icon`() {
        assertEquals(null, Routes.GameDetail.titleId)
        assertEquals(null, Routes.GameDetail.icon)
    }
}