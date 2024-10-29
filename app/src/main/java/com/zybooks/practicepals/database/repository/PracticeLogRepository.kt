package com.zybooks.practicepals.database.repository

import com.zybooks.practicepals.database.dao.PracticeLogDao
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.database.entities.PracticeLog
import kotlinx.coroutines.flow.Flow

class PracticeLogRepository (private val practiceLogDao: PracticeLogDao){

    fun getPracticeLogsForPiece(pieceId: Int): Flow<List<PracticeLog>> = practiceLogDao.getPracticeLogsForPiece(pieceId)

    suspend fun addPracticeLog(practiceLog: PracticeLog) {
        practiceLogDao.insertPracticeLogAndUpdatePiece(practiceLog, practiceLog.timeLogged)
    }
}