package com.zybooks.practicepals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.database.entities.PracticeLog
import com.zybooks.practicepals.database.repository.PracticeLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
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

    fun getPracticeLogsLast7Days(): Flow<List<PracticeLog>> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -6) // Include today and the previous 6 days
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        return practiceLogRepository.getPracticeLogsFrom(startTime)
    }

    fun removePracticeLog(practiceLog: PracticeLog) {
        viewModelScope.launch {
            practiceLogRepository.removePracticeLog(practiceLog)
        }
    }

    // ... other methods as needed ...
}
