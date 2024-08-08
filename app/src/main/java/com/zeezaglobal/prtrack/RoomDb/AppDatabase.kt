package com.zeezaglobal.prtrack.RoomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeezaglobal.prtrack.Dao.ExerciseDao
import com.zeezaglobal.prtrack.Dao.MuscleGroupDao
import com.zeezaglobal.prtrack.Entities.Exercise
import com.zeezaglobal.prtrack.Entities.MuscleGroup
import com.zeezaglobal.prtrack.Entities.User

@Database(entities = [Exercise::class, MuscleGroup::class,User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun muscleGroupDao(): MuscleGroupDao
}