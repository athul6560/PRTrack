package com.zeezaglobal.prtrack.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application)  {
    private val workoutDao: WorkoutDao = (application as MyApp).database.workoutDao()

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())
    val workouts: StateFlow<List<Workout>> get() = _workouts

    fun getWorkoutsByBodyPartId(bodyPartId: Int) {
        viewModelScope.launch {
            try {
                val workoutsList = workoutDao.getWorkoutsByBodyPartId(bodyPartId)
                _workouts.value = workoutsList
            } catch (e: Exception) {
                // Handle any errors here
                e.printStackTrace()
            }
        }
    }
}