package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.database.admin.AdminDatabaseManager
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.database.repository.PieceRepository
import com.zybooks.practicepals.database.repository.PracticeLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel    // Add this line
import javax.inject.Inject                            // Add this line
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel  // Add this annotation
class AdminViewModel @Inject constructor(    // Add @Inject before constructor
    private val adminDatabaseManager: AdminDatabaseManager
) : ViewModel() {

    fun clearDatabase() {
        viewModelScope.launch {
            adminDatabaseManager.clearDatabase()
        }
    }

    fun seedDatabase() {
        viewModelScope.launch {
            adminDatabaseManager.seedDatabase()
        }
    }
}
