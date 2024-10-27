package com.zybooks.practicepals.database.repository

import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.database.entities.Piece
import kotlinx.coroutines.flow.Flow

class PieceRepository(private val pieceDao: PieceDao) {

    fun getPieces(): Flow<List<Piece>> = pieceDao.getAllPieces()

    suspend fun addPiece(piece: Piece) = pieceDao.insert(piece)

    fun getPieceDetails(pieceId: Int): Flow<Piece> = pieceDao.getPieceById(pieceId)
}
