package com.zeezaglobal.prtrack.Entities

data class WorkoutWithLogs (
    val workout: Workout,
    val workoutLogs: List<WorkoutLog>
)
