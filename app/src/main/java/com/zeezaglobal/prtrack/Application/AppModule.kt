package com.zeezaglobal.prtrack.Application

import android.app.Application
import androidx.room.Room
import com.zeezaglobal.prtrack.Dao.ExerciseDao
import com.zeezaglobal.prtrack.Dao.ExerciseRecordDao
import com.zeezaglobal.prtrack.Database.AppDatabase
import com.zeezaglobal.prtrack.Repository.ExerciseRecordRepository
import com.zeezaglobal.prtrack.Repository.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "exercise_db"
        ).build()
    }

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao {
        return db.exerciseDao()
    }

    @Provides
    fun provideExerciseRecordDao(db: AppDatabase): ExerciseRecordDao {
        return db.exerciseRecordDao()
    }

    @Provides
    fun provideExerciseRepository(exerciseDao: ExerciseDao): ExerciseRepository {
        return ExerciseRepository(exerciseDao)
    }

    @Provides
    fun provideExerciseRecordRepository(exerciseRecordDao: ExerciseRecordDao): ExerciseRecordRepository {
        return ExerciseRecordRepository(exerciseRecordDao)
    }
}