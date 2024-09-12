package com.zeezaglobal.prtrack.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Entities.BodyPart
import com.zeezaglobal.prtrack.Repositories.BodyPartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BodyPartViewModel (private val repository: BodyPartRepository) : ViewModel() {

    // Get all body parts
    fun getAllBodyParts(): Flow<List<BodyPart>> {
        return repository.getAllBodyParts()
    }


    suspend fun getBodyPartIdByNameSuspend(bodyPartName: String): Int? {
        return repository.getBodyPartIdByName(bodyPartName)
    }

}