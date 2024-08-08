package com.zeezaglobal.prtrack.RoomDb

import android.app.Application
import androidx.room.Room

class MyApp: Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).build()
    }
}