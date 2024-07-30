package com.zeezaglobal.prtrack.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.prtrack.Entities.Exercise
import com.zeezaglobal.prtrack.Repository.ExerciseRepository
import kotlinx.coroutines.launch


class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {
    fun getExercisesByType(type: String) = repository.getExercisesByType(type)

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.insertExercise(exercise)
        }
    }
}