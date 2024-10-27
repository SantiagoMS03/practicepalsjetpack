package com.zybooks.practicepals.database.dao

import androidx.room.*
import androidx.room.Dao
import com.zybooks.practicepals.database.entities.Piece
import kotlinx.coroutines.flow.Flow

@Dao
interface PieceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(piece: Piece)

    @Update
    suspend fun update(piece: Piece)

    @Delete
    suspend fun delete(piece: Piece)

    @Query("SELECT * FROM pieces WHERE pieceId = :pieceId")
    fun getPieceById(pieceId: Int): Flow<Piece>

    @Query("SELECT * FROM pieces ORDER BY date_added DESC")
    fun getAllPieces(): Flow<List<Piece>>

    @Query("DELETE FROM pieces")
    suspend fun deleteAll()
}
