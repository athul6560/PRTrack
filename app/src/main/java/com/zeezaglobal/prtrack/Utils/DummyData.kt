package com.zeezaglobal.prtrack.Utils

import com.zeezaglobal.prtrack.Entities.Workout
import com.zeezaglobal.prtrack.Entities.WorkoutLog

val dummyWorkouts = listOf(
    Workout(name = "Chest Workout", bodyPart = "Chest"),
    Workout(name = "Leg Workout", bodyPart = "Legs")
)

val dummyLogs = listOf(
    WorkoutLog(workoutId = 1, date = System.currentTimeMillis(), weight = 60.0, sets = 3, reps = 10),
    WorkoutLog(workoutId = 1, date = System.currentTimeMillis() - 86400000, weight = 65.0, sets = 3, reps = 8),
    WorkoutLog(workoutId = 2, date = System.currentTimeMillis(), weight = 80.0, sets = 4, reps = 12)
)