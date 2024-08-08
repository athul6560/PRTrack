package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLog)

    @Update
    suspend fun updateWorkoutLog(log: WorkoutLog)

    @Delete
    suspend fun deleteWorkoutLog(log: WorkoutLog)

    @Query("SELECT * FROM workout_logs WHERE logId = :id")
    suspend fun getWorkoutLogById(id: Long): WorkoutLog?

    @Query("SELECT * FROM workout_logs WHERE workoutId = :workoutId")
    fun getLogsByWorkout(workoutId: Long): Flow<List<WorkoutLog>>

    @Query("SELECT * FROM workout_logs WHERE workoutId = :workoutId ORDER BY date ASC")
    fun getLogsByWorkoutSortedByDate(workoutId: Long): Flow<List<WorkoutLog>>
}