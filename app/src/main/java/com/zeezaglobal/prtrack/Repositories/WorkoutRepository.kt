package com.zeezaglobal.prtrack.Repositories

import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import kotlinx.coroutines.flow.Flow

class WorkoutRepository (private val workoutDao: WorkoutDao) {



    fun getAllWorkoutById(id:Int): Flow<List<Workout>> {
        return    workoutDao.getWorkoutsByBodyPartId(id)
    }

    // Insert a new workout
    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout)
    }


}