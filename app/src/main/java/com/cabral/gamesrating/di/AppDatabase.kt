package com.cabral.gamesrating.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cabral.gamesrating.data.local.GameDao
import com.cabral.gamesrating.data.local.GameFavoriteEntity

@Database(entities = [GameFavoriteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
