package com.zeezaglobal.prtrack.RoomDb

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.zeezaglobal.prtrack.Utils.populateDatabase

class MyApp : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).build()



        // Get SharedPreferences
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        // Check if the database has been populated before
        val isDatabasePopulated = sharedPreferences.getBoolean("isDatabasePopulated", false)

        if (!isDatabasePopulated) {
            // Populate the database with dummy data
            val bodyPartDao = database.bodyPartDao()
            populateDatabase(bodyPartDao)

            // Set the flag to true so it won't run again
            sharedPreferences.edit().putBoolean("isDatabasePopulated", true).apply()

            Log.d("MyApp", "Database populated with initial data")
        } else {
            Log.d("MyApp", "Database population skipped")
        }
        }
}