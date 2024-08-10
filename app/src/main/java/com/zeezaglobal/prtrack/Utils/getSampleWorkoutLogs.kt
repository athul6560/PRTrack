package com.zeezaglobal.prtrack.Utils

import com.zeezaglobal.prtrack.Entities.WorkoutLog
import java.util.Calendar

fun getSampleWorkoutLogs(): List<WorkoutLog> {
    val calendar = Calendar.getInstance()

    return listOf(
        // 1 week ago
        WorkoutLog(
            id = 1,
            weight = 50f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, -6) }.timeInMillis,
            workoutId = 1
        ),
        // 6 days ago
        WorkoutLog(
            id = 2,
            weight = 55f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            workoutId = 1
        ),
        // 5 days ago
        WorkoutLog(
            id = 3,
            weight = 60f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            workoutId = 2
        ),
        // 4 days ago
        WorkoutLog(
            id = 4,
            weight = 65f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            workoutId = 2
        ),
        // 3 days ago
        WorkoutLog(
            id = 5,
            weight = 70f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            workoutId = 3
        ),
        // 2 days ago
        WorkoutLog(
            id = 6,
            weight = 75f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            workoutId = 3
        ),
        // 1 day ago
        WorkoutLog(
            id = 7,
            weight = 80f,
            date = calendar.apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis,
            workoutId = 1
        ),
        // Today
        WorkoutLog(
            id = 8,
            weight = 85f,
            date = calendar.timeInMillis,
            workoutId = 2
        )
    )
}