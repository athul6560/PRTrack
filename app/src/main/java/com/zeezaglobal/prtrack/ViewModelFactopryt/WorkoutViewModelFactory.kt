package com.zeezaglobal.prtrack.ViewModelFactopryt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeezaglobal.prtrack.Repositories.WorkoutRepository
import com.zeezaglobal.prtrack.ViewModels.WorkoutViewModel

class WorkoutViewModelFactory(private val repository: WorkoutRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            return WorkoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}