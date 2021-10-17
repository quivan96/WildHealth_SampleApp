package com.example.wildhealth_sampleapp.viewmodel

import androidx.lifecycle.*
import com.example.wildhealth_sampleapp.model.remote.dto.Workout
import com.example.wildhealth_sampleapp.model.repository.WorkoutRepository
import com.example.wildhealth_sampleapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private var _workouts = MutableLiveData<Resource<List<Workout>>>()
    val workouts: LiveData<Resource<List<Workout>>> = _workouts

    fun getAllExercise() {
        viewModelScope.launch {
            workoutRepository.getAllExercises().collect {
                _workouts.postValue(it)
            }
        }
    }

    fun getWorkoutByName(name: String) {
        viewModelScope.launch {
            workoutRepository.getExerciseByName(name).collect {
                _workouts.postValue(it)
            }
        }
    }
}