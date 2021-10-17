package com.example.wildhealth_sampleapp.model.remote.networkservice

import com.example.wildhealth_sampleapp.model.remote.dto.Workout
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WorkoutService {

    @GET("exercises")
    suspend fun getAllExercises(): Response<List<Workout>>

    @GET("exercises/name/{name}")
    suspend fun getExerciseByName(
        @Path("name") name: String
    ): Response<List<Workout>>
}
