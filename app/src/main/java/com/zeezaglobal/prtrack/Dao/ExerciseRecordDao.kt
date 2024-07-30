package com.zeezaglobal.prtrack.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeezaglobal.prtrack.Entities.ExerciseRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseRecordDao {
    @Insert
    suspend fun insertRecord(record: ExerciseRecord)

    @Query("SELECT * FROM ExerciseRecord WHERE exerciseId = :exerciseId")
    fun getRecordsForExercise(exerciseId: Int): Flow<List<ExerciseRecord>>
}