package com.zeezaglobal.prtrack.RoomDb

import android.app.Application
import androidx.room.Room
import com.zeezaglobal.prtrack.Utils.populateDatabase

class MyApp: Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).build()

        val workoutDao = database.workoutDao()
        val workoutLogDao = database.workoutLogDao()
        // Populate the database with dummy data
        populateDatabase(workoutDao, workoutLogDao)
    }
}