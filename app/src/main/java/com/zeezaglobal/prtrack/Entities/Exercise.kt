package com.zeezaglobal.prtrack.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String // e.g., "chest", "biceps", etc.
)