package com.zeezaglobal.prtrack.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeezaglobal.prtrack.Dao.ExerciseDao
import com.zeezaglobal.prtrack.Dao.ExerciseRecordDao
import com.zeezaglobal.prtrack.Entities.Exercise
import com.zeezaglobal.prtrack.Entities.ExerciseRecord

@Database(entities = [Exercise::class, ExerciseRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseRecordDao(): ExerciseRecordDao
}