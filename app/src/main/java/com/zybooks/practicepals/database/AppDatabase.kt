package com.zybooks.practicepals.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.database.dao.PracticeLogDao
import com.zybooks.practicepals.database.entities.Piece
import com.zybooks.practicepals.database.entities.PracticeLog

@Database(entities = [Piece::class, PracticeLog::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pieceDao(): PieceDao
    abstract fun practiceLogDao(): PracticeLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
    }
}
