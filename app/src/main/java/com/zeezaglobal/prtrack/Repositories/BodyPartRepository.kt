package com.zeezaglobal.prtrack.Repositories

import com.zeezaglobal.prtrack.Dao.BodyPartDao
import com.zeezaglobal.prtrack.Entities.BodyPart
import kotlinx.coroutines.flow.Flow

class BodyPartRepository (private val bodyPartDao: BodyPartDao) {

    // Get all body parts
    fun getAllBodyParts(): Flow<List<BodyPart>> {
        return bodyPartDao.getAllBodyParts()
    }

    suspend fun getBodyPartIdByName(bodyPartName: String): Int? {
        return bodyPartDao.getBodyPartIdByName(bodyPartName)
    }
}