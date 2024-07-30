package com.zeezaglobal.prtrack.Repository

import com.zeezaglobal.prtrack.Dao.ExerciseDao
import com.zeezaglobal.prtrack.Entities.Exercise
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    fun getExercisesByType(type: String): Flow<List<Exercise>> {
        return exerciseDao.getExercisesByType(type)
    }

    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
}