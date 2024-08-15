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


    @Query("SELECT workout_log.*, workout.workoutName FROM workout_log INNER JOIN workout ON workout_log.workoutId = workout.id WHERE workout_log.workoutId = :workoutId")
    fun getWorkoutLogsByWorkoutId(workoutId: Int): Flow<List<WorkoutLogWithName>>

    @Transaction
    @Query(" SELECT workout.id AS workoutId, workout.workoutName, workout.bodyPartId, workout_log.id AS logId, workout_log.weight, workout_log.date, workout_log.workoutId AS logWorkoutId FROM workout LEFT JOIN workout_log ON workout.id = workout_log.workoutId WHERE workout.id = :workoutId")
    suspend fun getWorkoutWithLogsById(workoutId: Int): WorkoutWithLogs?
}