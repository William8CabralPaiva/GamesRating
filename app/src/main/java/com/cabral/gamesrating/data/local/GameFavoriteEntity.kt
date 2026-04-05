package com.cabral.gamesrating.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cabral.gamesrating.di.Converters
import com.cabral.gamesrating.ui.model.GameUi

@Entity(
    tableName = "game_favorites",
    indices = [Index(value = ["id"], unique = true)]
)
@TypeConverters(Converters::class)
data class GameFavoriteEntity(
    val id: Int,
    @PrimaryKey(autoGenerate = true)
    val orderId: Int?,
    val name: String,
    val genres: String?,
    val released: String?,
    val rating: Double?,
    val backgroundImage: String?,
)


fun GameUi.toGameFavoriteEntity(): GameFavoriteEntity {
    return GameFavoriteEntity(
        id = id,
        name = name,
        backgroundImage = backgroundImage,
        rating = rating,
        genres = genres,
        released = released,
        orderId = null
    )
}

