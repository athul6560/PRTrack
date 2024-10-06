package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zeezaglobal.prtrack.Entities.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Transaction
    @Query("SELECT * FROM workout WHERE bodyPartId = :bodyPartId")
     fun getWorkoutsByBodyPartId(bodyPartId: Int): Flow<List<Workout>>

    @Query("SELECT id FROM workout WHERE workoutName = :workoutName LIMIT 1")
    fun getWorkoutIdByName(workoutName: String): Int
}