package com.example.wildhealth_sampleapp

import androidx.test.core.app.ApplicationProvider
import com.example.wildhealth_sampleapp.model.remote.dto.Workout
import com.example.wildhealth_sampleapp.model.remote.networkservice.WorkoutService
import com.example.wildhealth_sampleapp.model.repository.WorkoutRepository
import com.example.wildhealth_sampleapp.model.repository.WorkoutRepositoryImpl
import com.example.wildhealth_sampleapp.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
class WorkoutRepositoryTest {

    @MockK
    lateinit var workoutService: WorkoutService

    private lateinit var workoutRepository: WorkoutRepository

    @Before
    fun init() {
        MockKAnnotations.init(this)

        workoutRepository =
            WorkoutRepositoryImpl(ApplicationProvider.getApplicationContext(), workoutService, Dispatchers.IO)
    }

    @Test
    fun `getAllExercises returns Resource Success`() {
        val exerciseList = listOf(
            Workout(
                "arms",
                "barbell",
                "google.com",
                "12",
                "Curl",
                "bicep"
            ),
            Workout(
                "arms",
                "barbell",
                "google.com",
                "12",
                "Curl",
                "bicep"
            ),
            Workout(
                "arms",
                "barbell",
                "google.com",
                "12",
                "Curl",
                "bicep"
            )
        )
        coEvery { workoutService.getAllExercises() } returns Response.success(exerciseList)

        assertEquals(Resource.Success(exerciseList), runBlocking { workoutRepository.getAllExercises().last() })
    }

    @Test
    fun `getAllExercises returns Resource Error when empty`() {
        coEvery { workoutService.getAllExercises() } returns Response.success(emptyList())

        assertTrue(runBlocking { workoutRepository.getAllExercises().last() } is Resource.Error)
    }


    @Test
    fun `getExerciseByName returns Resource Success`() {
        val exerciseList = listOf(
            Workout(
                "arms",
                "barbell",
                "google.com",
                "12",
                "Curl",
                "bicep"
            ),
            Workout(
                "arms",
                "barbell",
                "google.com",
                "12",
                "Curl",
                "bicep"
            ),
            Workout(
                "arms",
                "barbell",
                "google.com",
                "12",
                "Curl",
                "bicep"
            )
        )
        coEvery { workoutService.getExerciseByName("Curl") } returns Response.success(exerciseList)

        assertEquals(Resource.Success(exerciseList), runBlocking { workoutRepository.getExerciseByName("Curl").last() })
    }

    @Test
    fun `getExerciseByName returns Resource Error when empty`() {
        coEvery { workoutService.getExerciseByName(any()) } returns Response.success(emptyList())

        assertTrue(runBlocking { workoutRepository.getExerciseByName("Curl").last() } is Resource.Error)
    }

}