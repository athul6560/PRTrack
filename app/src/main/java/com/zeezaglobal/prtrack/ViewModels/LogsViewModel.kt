package com.zeezaglobal.prtrack.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp

class LogsViewModel (application: Application) : AndroidViewModel(application) {
    private val workoutLogDao: WorkoutLogDao =(application as MyApp).database.workoutLogDao()

    fun getWorkoutLogs(workoutId: Long): LiveData<List<WorkoutLog>> {
        return workoutLogDao.getLogsByWorkoutSortedByDate(workoutId).asLiveData()
    }
}