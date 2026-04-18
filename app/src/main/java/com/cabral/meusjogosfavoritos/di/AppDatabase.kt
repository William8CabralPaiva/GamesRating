package com.cabral.meusjogosfavoritos.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cabral.meusjogosfavoritos.data.local.GameDao
import com.cabral.meusjogosfavoritos.data.local.GameFavoriteEntity

@Database(entities = [GameFavoriteEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
