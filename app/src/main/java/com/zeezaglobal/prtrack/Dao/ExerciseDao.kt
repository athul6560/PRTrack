package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeezaglobal.prtrack.Entities.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM Exercise WHERE type = :type")
    fun getExercisesByType(type: String): Flow<List<Exercise>>
}