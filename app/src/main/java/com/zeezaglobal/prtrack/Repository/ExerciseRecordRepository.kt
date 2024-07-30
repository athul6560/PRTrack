package com.zeezaglobal.prtrack.Repository

import com.zeezaglobal.prtrack.Dao.ExerciseRecordDao
import com.zeezaglobal.prtrack.Entities.ExerciseRecord
import kotlinx.coroutines.flow.Flow

class ExerciseRecordRepository(private val exerciseRecordDao: ExerciseRecordDao) {
    fun getRecordsForExercise(exerciseId: Int): Flow<List<ExerciseRecord>> {
        return exerciseRecordDao.getRecordsForExercise(exerciseId)
    }

    suspend fun insertRecord(record: ExerciseRecord) {
        exerciseRecordDao.insertRecord(record)
    }
}