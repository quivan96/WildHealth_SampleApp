package com.example.wildhealth_sampleapp.di

import com.example.wildhealth_sampleapp.model.remote.networkservice.WorkoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesWorkoutService(retrofit: Retrofit) = retrofit.create(WorkoutService::class.java)
}