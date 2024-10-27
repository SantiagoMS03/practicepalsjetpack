package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.database.repository.PieceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PieceViewModel (private val pieceRepository: PieceRepository) : ViewModel() {
    val piecesFlow = pieceRepository.getPieces()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addPiece(piece: Piece) {
        viewModelScope.launch {
            pieceRepository.addPiece(piece = piece)
        }
    }

    fun getPieceDetails(pieceId: Int): Flow<Piece> {
        return pieceRepository.getPieceDetails(pieceId)
    }
}