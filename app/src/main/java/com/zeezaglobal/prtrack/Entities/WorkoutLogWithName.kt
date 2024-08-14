package com.zeezaglobal.prtrack.Entities

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class WorkoutLogWithName(
    @Embedded val workoutLog: WorkoutLog,
    @ColumnInfo(name = "workoutName") val workoutName: String
)