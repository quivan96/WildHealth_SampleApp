package com.example.wildhealth_sampleapp.model.repository

import com.example.wildhealth_sampleapp.model.remote.dto.Workout
import com.example.wildhealth_sampleapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun getAllExercises(): Flow<Resource<List<Workout>>>

    fun getExerciseByName(name: String): Flow<Resource<List<Workout>>>

}
