package com.zybooks.practicepals.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pieces")
data class Piece(
    @PrimaryKey(autoGenerate = true)
    val pieceId: Int = 0, // Default value allows Room to auto-generate

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "composer")
    val composer: String,

    @ColumnInfo(name = "date_added")
    val dateAdded: Long = System.currentTimeMillis(),  // Unix timestamp for easier handling of dates

    @ColumnInfo(name = "total_time_practiced")
    val totalTimePracticed: Long = 0  // Default value is 0
)
