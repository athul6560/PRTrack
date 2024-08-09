package com.zeezaglobal.prtrack.Utils

import com.zeezaglobal.prtrack.Dao.BodyPartDao
import com.zeezaglobal.prtrack.Dao.WorkoutDao
import com.zeezaglobal.prtrack.Dao.WorkoutLogDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun populateDatabase(bodyPartDao: BodyPartDao) {
    // Insert dummy workouts
    GlobalScope.launch {

        dummybodyparts.forEach { workout ->
            bodyPartDao.insertBodyPart(workout)
        }



    }
}