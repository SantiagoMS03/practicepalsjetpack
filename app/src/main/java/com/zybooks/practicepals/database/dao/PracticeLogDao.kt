package com.zybooks.practicepals.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zybooks.practicepals.database.entities.PracticeLog
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(practiceLog: PracticeLog)  // Return the new row ID

    @Update
    suspend fun update(practiceLog: PracticeLog)

    @Delete
    suspend fun delete(practiceLog: PracticeLog)

    @Query("SELECT * FROM practice_logs WHERE practice_log_id = :practiceLogId")
    fun getPracticeLogById(practiceLogId: Int): Flow<PracticeLog>

    @Query("SELECT * FROM practice_logs WHERE piece_id = :pieceId")
    fun getPracticeLogsForPiece(pieceId: Int): Flow<List<PracticeLog>>

    @Transaction
    suspend fun insertPracticeLogAndUpdatePiece(practiceLog: PracticeLog, timeToAdd: Long) {
        insert(practiceLog)
        updatePieceTotalTime(practiceLog.pieceId, timeToAdd)
    }

    @Query("UPDATE pieces SET total_time_practiced = total_time_practiced + :timeToAdd WHERE piece_id = :pieceId")
    suspend fun updatePieceTotalTime(pieceId: Int, timeToAdd: Long)


}
