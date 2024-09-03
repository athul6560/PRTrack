package com.zeezaglobal.prtrack.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.Entities.WorkoutLogWithName
import com.zeezaglobal.prtrack.Entities.WorkoutWithLogs
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class LogsViewModel(application: Application) : AndroidViewModel(application) {
    private val workoutLogDao: WorkoutLogDao = (application as MyApp).database.workoutLogDao()

    private val _workoutLogs = MutableStateFlow<List<WorkoutLog>>(emptyList())
    val workoutLogs: StateFlow<List<WorkoutLog>> get() = _workoutLogs

    fun getWorkoutLogsByWorkoutId(workoutId: Int) {
        viewModelScope.launch {
            try {
                val logs = workoutLogDao.getWorkoutLogsByWorkoutId(workoutId)
                _workoutLogs.value = logs
            } catch (e: Exception) {
                // Handle any errors here
                e.printStackTrace()
            }
        }
    }
}