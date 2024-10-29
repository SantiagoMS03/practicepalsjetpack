package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.database.entities.PracticeLog
import com.zybooks.practicepals.database.repository.PracticeLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeLogViewModel @Inject constructor(
    private val practiceLogRepository: PracticeLogRepository
) : ViewModel() {

    fun addPracticeLog(practiceLog: PracticeLog) {
        viewModelScope.launch {
            practiceLogRepository.addPracticeLog(practiceLog)
        }
    }

    fun getPracticeLogsForPiece(pieceId: Int): Flow<List<PracticeLog>> {
        return practiceLogRepository.getPracticeLogsForPiece(pieceId)
    }

    // ... other methods as needed ...
}
