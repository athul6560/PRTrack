package com.zeezaglobal.prtrack.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout",
    foreignKeys = [ForeignKey(
        entity = BodyPart::class,
        parentColumns = ["id"],
        childColumns = ["bodyPartId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workoutName: String,
    val bodyPartId: Int
)