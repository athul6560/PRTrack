package com.zeezaglobal.prtrack.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Entities.ExerciseRecord
import com.zeezaglobal.prtrack.Repository.ExerciseRecordRepository
import kotlinx.coroutines.launch

class ExerciseRecordViewModel(private val repository: ExerciseRecordRepository) : ViewModel() {
    fun getRecordsForExercise(exerciseId: Int) = repository.getRecordsForExercise(exerciseId)

    fun addRecord(record: ExerciseRecord) {
        viewModelScope.launch {
            repository.insertRecord(record)
        }
    }
}