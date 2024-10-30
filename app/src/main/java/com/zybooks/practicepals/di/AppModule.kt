// AppModule.kt
package com.zybooks.practicepals.di

import android.content.Context
import androidx.room.Room
import com.zybooks.practicepals.database.AppDatabase
import com.zybooks.practicepals.database.dao.PieceDao
import com.zybooks.practicepals.database.dao.PracticeLogDao
import com.zybooks.practicepals.database.repository.PieceRepository
import com.zybooks.practicepals.database.repository.PracticeLogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide AppDatabase as a Singleton
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "practice_pals_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    // Provide DAOs
    @Provides
    fun providePieceDao(appDatabase: AppDatabase): PieceDao = appDatabase.pieceDao()

    @Provides
    fun providePracticeLogDao(appDatabase: AppDatabase): PracticeLogDao = appDatabase.practiceLogDao()

    // Provide Repositories as Singletons
    @Provides
    @Singleton
    fun providePieceRepository(pieceDao: PieceDao): PieceRepository = PieceRepository(pieceDao)

    @Provides
    @Singleton
    fun providePracticeLogRepository(practiceLogDao: PracticeLogDao): PracticeLogRepository = PracticeLogRepository(practiceLogDao)

    // Provide Metronome Tempo
    @Provides
    @Named("MetronomeTempo")
    fun provideMetronomeTempo(): Int = 60  // Default BPM

    // Provide Metronome Numerator
    @Provides
    @Named("MetronomeNumerator")
    fun provideMetronomeNumerator(): Int = 4  // Default Time Signature Numerator

    // No longer need to provide Metronome here as it's provided via @Inject constructor in Metronome.kt
}
