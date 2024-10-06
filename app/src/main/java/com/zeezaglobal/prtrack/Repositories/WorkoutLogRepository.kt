package com.zeezaglobal.prtrack.Repositories

import com.zeezaglobal.prtrack.Dao.BodyPartDao
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import com.zeezaglobal.prtrack.Entities.BodyPart
import com.zeezaglobal.prtrack.Entities.WorkoutLog
import kotlinx.coroutines.flow.Flow

class WorkoutLogRepository(private val workoutLogDao: WorkoutLogDao) {

     fun getAllWorkoutLogById(id:Int): Flow<List<WorkoutLog>> {
        return    workoutLogDao.getWorkoutLogsByWorkoutId(id)
    }
}