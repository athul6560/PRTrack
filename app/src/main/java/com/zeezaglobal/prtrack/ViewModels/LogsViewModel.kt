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
import com.zeezaglobal.prtrack.RoomDb.AppDatabase
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogsViewModel (application: Application) : AndroidViewModel(application) {
    private val workoutLogDao: WorkoutLogDao =(application as MyApp).database.workoutLogDao()



    private val _workoutLogs = MutableStateFlow<List<WorkoutLogWithName>>(emptyList())
    val workoutLogs: StateFlow<List<WorkoutLogWithName>> get() = _workoutLogs

    fun getWorkoutLogs(workoutId: Int) {
        viewModelScope.launch {
            workoutLogDao.getWorkoutLogsByWorkoutId(workoutId).collect { logs ->
                Log.d("LogsViewModel", "Fetched logs: $logs")
                _workoutLogs.value = logs
            }
        }
    }
}