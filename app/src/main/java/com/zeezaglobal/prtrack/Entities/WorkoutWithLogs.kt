package com.zeezaglobal.prtrack.Entities

data class WorkoutWithLogs(
    val workoutId: Int,
    val workoutName: String,
    val bodyPartId: Int,
    val logs: List<WorkoutLog>
)