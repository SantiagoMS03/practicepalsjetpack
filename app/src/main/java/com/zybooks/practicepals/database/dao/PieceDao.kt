package com.zybooks.practicepals.database.dao

import androidx.room.*
import com.zybooks.practicepals.database.entities.Piece
import kotlinx.coroutines.flow.Flow

@Dao
interface PieceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(piece: Piece)  // Return the new row ID

    @Update
    suspend fun update(piece: Piece)   // Return the number of rows updated

    @Delete
    suspend fun delete(piece: Piece)   // Return the number of rows deleted

    @Query("SELECT * FROM pieces WHERE piece_id = :pieceId")
    fun getPieceById(pieceId: Int): Flow<Piece>

    @Query("SELECT * FROM pieces ORDER BY date_added DESC")
    fun getAllPieces(): Flow<List<Piece>>

    @Query("DELETE FROM pieces")
    suspend fun deleteAll()            // Return the number of rows deleted
}
