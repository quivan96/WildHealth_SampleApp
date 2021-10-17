package com.example.wildhealth_sampleapp.model.repository

import android.content.Context
import com.example.wildhealth_sampleapp.R
import com.example.wildhealth_sampleapp.di.IoDispatcher
import com.example.wildhealth_sampleapp.model.remote.dto.Workout
import com.example.wildhealth_sampleapp.model.remote.networkservice.WorkoutService
import com.example.wildhealth_sampleapp.utils.Resource
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    @ActivityContext private val context: Context,
    private val workoutService: WorkoutService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    ) : WorkoutRepository {

    override fun getAllExercises(): Flow<Resource<List<Workout>>> = flow {
        emit(Resource.Loading)
        val resource = try {
            val response = workoutService.getAllExercises()
            if (response.isSuccessful && response.body().isNullOrEmpty().not()) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(context.getString(R.string.no_exercise_found_message))
            }
        } catch (ex: Exception) {
            Resource.Error(ex.toString())
        }
        emit(resource)
    }.flowOn(dispatcher)

    override fun getExerciseByName(name: String): Flow<Resource<List<Workout>>> = flow {
        emit(Resource.Loading)
        val resource = try {
            val response = workoutService.getExerciseByName(name)
            if (response.isSuccessful && response.body().isNullOrEmpty().not()) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(context.getString(R.string.no_exercise_found_message))
            }
        } catch (ex: Exception) {
            Resource.Error(ex.toString())
        }
        emit(resource)
    }.flowOn(dispatcher)

    companion object {
        private val TAG = WorkoutRepositoryImpl::class.java.name
    }
}
