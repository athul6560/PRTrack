package com.zeezaglobal.prtrack.ViewModelFactopryt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeezaglobal.prtrack.Repositories.BodyPartRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutLogRepository
import com.zeezaglobal.prtrack.Repositories.WorkoutRepository
import com.zeezaglobal.prtrack.ViewModels.BodyPartViewModel

class BodyPartViewModelFactory(
    private val bodyPartRepository: BodyPartRepository,
    private val workoutRepository: WorkoutRepository,
    private val workoutLogRepository: WorkoutLogRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BodyPartViewModel::class.java)) {
            return BodyPartViewModel(bodyPartRepository, workoutRepository, workoutLogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")}}
