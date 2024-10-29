package com.zybooks.practicepals.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "practice_logs",
        foreignKeys = [ForeignKey(
            entity = Piece::class,
            parentColumns = ["piece_id"],
            childColumns = ["piece_id"]
        )]
)
data class PracticeLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "practice_log_id")
    val practiceLogId: Int = 0, // Default value allows Room to auto-generate

    @ColumnInfo(name = "piece_id")
    val pieceId: Int,

    @ColumnInfo(name = "date_added")
    val dateLogged: Long = System.currentTimeMillis(),  // Unix timestamp for easier handling of dates

    @ColumnInfo(name = "total_time_practiced")
    val timeLogged: Long = 0,

    @ColumnInfo(name = "practice_log_description")
    val practiceLogDescription: String = " "
)
