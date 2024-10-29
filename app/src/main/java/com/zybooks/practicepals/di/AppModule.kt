package com.zybooks.practicepals.di

import android.content.Context
import androidx.room.Room
import com.zybooks.practicepals.database.AppDatabase
import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.database.repository.PieceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide AppDatabase
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "practice_pals_db"
        ).build()
    }

    // Provide PieceDao
    @Provides
    fun providePieceDao(appDatabase: AppDatabase): PieceDao {
        return appDatabase.pieceDao()
    }

    // Provide PieceRepository
    @Provides
    fun providePieceRepository(pieceDao: PieceDao): PieceRepository {
        return PieceRepository(pieceDao)
    }
}
