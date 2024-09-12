package com.zeezaglobal.prtrack.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.Repositories.WorkoutRepository
import com.zeezaglobal.prtrack.RoomDb.MyApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {



    // Insert a new workout
    fun insertWorkout(workoutName: Workout) {
        viewModelScope.launch {

            repository.insertWorkout(workoutName)
        }
    }


}