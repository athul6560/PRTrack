package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import com.zeezaglobal.prtrack.Entities.WorkoutLogWithName
import com.zeezaglobal.prtrack.Entities.WorkoutWithLogs
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLog)

    @Update
    suspend fun updateWorkoutLog(log: WorkoutLog)

    @Delete
    suspend fun deleteWorkoutLog(log: WorkoutLog)

    @Query("SELECT * FROM workout_log WHERE workoutId = :workoutId")
    suspend fun getWorkoutLogsByWorkoutId(workoutId: Int): List<WorkoutLog>

/*    @Query("SELECT workout_log.*, workout.workoutName FROM workout_log INNER JOIN workout ON workout_log.workoutId = workout.id WHERE workout_log.workoutId = :workoutId")
    fun getWorkoutLogsByWorkoutId(workoutId: Int): Flow<List<WorkoutLogWithName>>*/



}