package com.zeezaglobal.prtrack.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true) val workoutId: Long = 0,
    val name: String,         // Name of the workout
    val bodyPart: String      // Body part targeted by the workout (e.g., Chest, Legs)
)