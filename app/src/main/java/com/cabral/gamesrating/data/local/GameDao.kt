package com.cabral.gamesrating.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(game: GameFavoriteEntity)

    @Query("SELECT * FROM game_favorites ORDER BY orderId ASC")
    fun getAllFavorites(): Flow<List<GameFavoriteEntity>>

    @Query("DELETE FROM game_favorites WHERE id = :gameId")
    suspend fun deleteFavoriteById(gameId: Int)
}
