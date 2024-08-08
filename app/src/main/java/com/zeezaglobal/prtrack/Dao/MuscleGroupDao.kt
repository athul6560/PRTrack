package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zeezaglobal.prtrack.Entities.MuscleGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface MuscleGroupDao {
    @Query("SELECT * FROM muscle_groups")
    fun getAllMuscleGroups(): Flow<List<MuscleGroup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscleGroup(muscleGroup: MuscleGroup)
}