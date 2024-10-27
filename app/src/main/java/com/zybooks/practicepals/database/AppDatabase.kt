package com.zybooks.practicepals.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.database.entities.Piece

@Database(entities = [Piece::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pieceDao(): PieceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "practice_pals_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
