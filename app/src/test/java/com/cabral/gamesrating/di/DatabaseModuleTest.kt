package com.cabral.gamesrating.di

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cabral.gamesrating.data.local.GameDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import androidx.room.Room
import androidx.room.RoomDatabase
import org.junit.Rule

class DatabaseModuleTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `provideGameDao should return gameDao from database`() {
        // GIVEN
        val database = mockk<AppDatabase>()
        val expectedDao = mockk<GameDao>()
        every { database.gameDao() } returns expectedDao

        // WHEN
        val result = DatabaseModule.provideGameDao(database)

        // THEN
        assertEquals(expectedDao, result)
        verify(exactly = 1) { database.gameDao() }
    }

    @Test
    fun `provideAppDatabase should return a built Room database`() {
        // GIVEN
        val context = mockk<Context>(relaxed = true)
        val builder = mockk<RoomDatabase.Builder<AppDatabase>>(relaxed = true)
        val database = mockk<AppDatabase>()

        mockkStatic(Room::class)
        every {
            Room.databaseBuilder(context, AppDatabase::class.java, "games_database")
        } returns builder
        every { builder.build() } returns database

        // WHEN
        val result = DatabaseModule.provideAppDatabase(context)

        // THEN
        assertEquals(database, result)
        verify { Room.databaseBuilder(context, AppDatabase::class.java, "games_database") }
        verify { builder.build() }
    }
}