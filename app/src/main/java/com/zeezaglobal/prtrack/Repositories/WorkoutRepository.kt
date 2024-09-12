package com.zeezaglobal.prtrack.Repositories

import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Entities.Workout
import kotlinx.coroutines.flow.Flow

class WorkoutRepository (private val workoutDao: WorkoutDao) {





    // Insert a new workout
    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout)
    }


}