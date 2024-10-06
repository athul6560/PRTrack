package com.zeezaglobal.prtrack.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Entities.BodyPart
import com.zeezaglobal.prtrack.Entities.WorkoutWithLogs
import com.zeezaglobal.prtrack.Repositories.BodyPartRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutLogRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BodyPartViewModel (
    private val repository: BodyPartRepository,
    private val workoutRepository: WorkoutRepository,
    private val workoutLogRepository: WorkoutLogRepository
) : ViewModel() {
    private val _workoutsWithLogs = MutableLiveData<List<WorkoutWithLogs>>(emptyList())
    val workoutsWithLogs: LiveData<List<WorkoutWithLogs>> get() = _workoutsWithLogs
    // Get all body parts

    fun getWorkoutsWithLogsByBodyPartId(bodyPartId: Int) {
        viewModelScope.launch {
            try {
                // Fetch the workouts by bodyPartId as a Flow
                val workoutsFlow = workoutRepository.getAllWorkoutById(bodyPartId)

                // Combine each workout with its corresponding logs
                workoutsFlow
                    .collect { workouts ->
                        // For each workout, get the logs using flows and combine them
                        val combinedFlow = combine(
                            workouts.map { workout ->
                                workoutLogRepository.getAllWorkoutLogById(workout.id).map { logs ->
                                    WorkoutWithLogs(workout, logs)
                                }
                            }
                        ) { combinedLogs ->
                            combinedLogs.toList()
                        }

                        // Update the state flow with the combined data
                        combinedFlow
                            .flowOn(Dispatchers.IO)
                            .collect { combinedList ->
                                _workoutsWithLogs.value = combinedList
                            }
                    }

            } catch (e: Exception) {
                // Handle any errors here
                e.printStackTrace()
            }
        }
    }

    suspend fun getBodyPartIdByNameSuspend(bodyPartName: String): Int? {
        return repository.getBodyPartIdByName(bodyPartName)
    }

}