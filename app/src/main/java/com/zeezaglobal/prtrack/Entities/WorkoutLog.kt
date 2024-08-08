package com.zeezaglobal.prtrack.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_logs",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["workoutId"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["workoutId"])]
)
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0,
    val workoutId: Long,       // Foreign key to Workout
    val date: Long,            // Date of the workout session (stored as a timestamp)
    val weight: Double,        // Weight lifted in kg
    val sets: Int,             // Number of sets
    val reps: Int              // Number of reps per set
)