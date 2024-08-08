package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM workouts WHERE workoutId = :id")
    suspend fun getWorkoutById(id: Long): Workout?

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): Flow<List<Workout>>
}