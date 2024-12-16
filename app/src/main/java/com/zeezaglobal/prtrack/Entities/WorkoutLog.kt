package com.zeezaglobal.prtrack.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "workout_log",
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: Float,
    val date: Long,
    val workoutId: Int,
    val sets: Int
)