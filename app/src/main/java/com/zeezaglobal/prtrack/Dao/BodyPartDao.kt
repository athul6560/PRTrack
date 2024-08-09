package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.zeezaglobal.prtrack.Entities.BodyPart
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyPartDao {

    @Insert
    suspend fun insertBodyPart(bodyPart: BodyPart): Long

    @Update
    suspend fun updateBodyPart(bodyPart: BodyPart)

    @Delete
    suspend fun deleteBodyPart(bodyPart: BodyPart)

    @Query("SELECT * FROM body_part WHERE id = :id LIMIT 1")
    fun getBodyPartById(id: Int): Flow<BodyPart?>

    @Query("SELECT * FROM body_part")
    fun getAllBodyParts(): Flow<List<BodyPart>>

    @Query("SELECT id FROM body_part WHERE bodyPartName = :name LIMIT 1")
    suspend fun getBodyPartIdByName(name: String): Int?
}