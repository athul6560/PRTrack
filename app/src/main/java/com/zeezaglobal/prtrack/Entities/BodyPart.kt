package com.zeezaglobal.prtrack.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "body_part")
data class BodyPart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bodyPartName: String
)