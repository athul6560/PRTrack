package com.zeezaglobal.prtrack.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.Entities.WorkoutLog

@Database(entities = [Workout::class, WorkoutLog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutLogDao(): WorkoutLogDao
}