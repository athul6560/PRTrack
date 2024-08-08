package com.zeezaglobal.prtrack.Utils

import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun populateDatabase(workoutDao: WorkoutDao, workoutLogDao: WorkoutLogDao) {
    // Insert dummy workouts
    GlobalScope.launch {
        workoutDao.insertWorkout(dummyWorkouts[0])
        workoutDao.insertWorkout(dummyWorkouts[1])

        // Retrieve the inserted workout IDs
        val workouts = workoutDao.getAllWorkouts().first()

        // Insert dummy logs for each workout
        val workout1Id = workouts.find { it.name == "Chest Workout" }?.workoutId ?: 0
        val workout2Id = workouts.find { it.name == "Leg Workout" }?.workoutId ?: 0

        dummyLogs.forEach { log ->
            log.copy(workoutId = workout1Id).let {
                workoutLogDao.insertWorkoutLog(it)
            }
            log.copy(workoutId = workout2Id).let {
                workoutLogDao.insertWorkoutLog(it)
            }
        }
    }
}