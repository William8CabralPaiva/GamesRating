package com.cabral.gamesrating.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cabral.gamesrating.di.Converters

@Entity(
    tableName = "game_favorites",
    indices = [Index(value = ["id"], unique = true)]
)
@TypeConverters(Converters::class)
data class GameFavoriteEntity(
    val id: Int,
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val name: String,
    val description: String?,
    val platforms: String?,
    val genres: String?,
    val released: String?,
    val rating: Double?,
    val screenshots: List<String>?,
    val backgroundImage: String?,
)


