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

    @Delete
    suspend fun deleteFavorite(game: GameFavoriteEntity)

    @Query("SELECT * FROM game_favorites ORDER BY orderId ASC")
    fun getAllFavorites(): Flow<List<GameFavoriteEntity>>

//    @Query("SELECT * FROM game_favorites WHERE id = :gameId LIMIT 1")
//    fun getFavoriteByGameId(gameId: Int): Flow<GameFavoriteEntity?>

//    @Query("SELECT MAX(orderId) FROM game_favorites")
//    suspend fun getMaxOrderId(): Int?
}
