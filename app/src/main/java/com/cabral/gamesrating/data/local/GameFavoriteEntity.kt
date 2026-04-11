package com.cabral.gamesrating.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cabral.gamesrating.di.Converters
import com.cabral.gamesrating.ui.model.GameUi

@Entity(tableName = "game_favorites")
@TypeConverters(Converters::class)
data class GameFavoriteEntity(
    @PrimaryKey
    val id: Int,
    val orderId: Int,
    val name: String,
    val genres: String?,
    val released: String?,
    val rating: Double?,
    val backgroundImage: String?,
)


fun GameUi.toGameFavoriteEntity(): GameFavoriteEntity {
    return GameFavoriteEntity(
        id = id,
        orderId = orderId ?: 0,
        name = name,
        backgroundImage = backgroundImage,
        rating = rating,
        genres = genres,
        released = released
    )
}

fun GameFavoriteEntity.toGameUi(): GameUi {
    return GameUi(
        id = id,
        name = name,
        released = released,
        backgroundImage = backgroundImage,
        rating = rating ?: 0.0,
        genres = genres,
        isFavorite = true,
        orderId = orderId
    )
}
