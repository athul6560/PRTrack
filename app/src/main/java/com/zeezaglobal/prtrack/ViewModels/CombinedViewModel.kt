package com.zeezaglobal.prtrack.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import com.zeezaglobal.prtrack.Entities.WorkoutWithLogs
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CombinedViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutDao: WorkoutDao = (application as MyApp).database.workoutDao()
    private val workoutLogDao: WorkoutLogDao = (application as MyApp).database.workoutLogDao()

    private val _workoutsWithLogs = MutableStateFlow<List<WorkoutWithLogs>>(emptyList())
    val workoutsWithLogs: StateFlow<List<WorkoutWithLogs>> get() = _workoutsWithLogs

    fun getWorkoutsWithLogsByBodyPartId(bodyPartId: Int) {
        viewModelScope.launch {
            try {
                // Fetch the workouts by bodyPartId
                val workouts = workoutDao.getWorkoutsByBodyPartId(bodyPartId)

                // Combine each workout with its corresponding logs
                val combinedList = workouts.map { workout ->
                    val logs = workoutLogDao.getWorkoutLogsByWorkoutId(workout.id)
                    WorkoutWithLogs(workout, logs)
                }

                // Update the state flow with the combined data
                _workoutsWithLogs.value = combinedList
            } catch (e: Exception) {
                // Handle any errors here
                e.printStackTrace()
            }
        }
    }
}