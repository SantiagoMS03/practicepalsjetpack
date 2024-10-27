package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zybooks.practicepals.database.repository.PieceRepository

class PieceViewModelFactory(
    private val pieceRepository: PieceRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PieceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PieceViewModel(pieceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
