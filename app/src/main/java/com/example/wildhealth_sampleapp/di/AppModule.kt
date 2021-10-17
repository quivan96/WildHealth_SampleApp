package com.example.wildhealth_sampleapp.di

import android.content.Context
import com.example.wildhealth_sampleapp.model.remote.networkservice.WorkoutService
import com.example.wildhealth_sampleapp.model.repository.WorkoutRepository
import com.example.wildhealth_sampleapp.model.repository.WorkoutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesWorkoutService(retrofit: Retrofit) = retrofit.create(WorkoutService::class.java)

    @Singleton
    @Provides
    fun providesWorkoutRepository(
        @ApplicationContext context: Context,
        workoutService: WorkoutService
    ): WorkoutRepository = WorkoutRepositoryImpl(context, workoutService)
}