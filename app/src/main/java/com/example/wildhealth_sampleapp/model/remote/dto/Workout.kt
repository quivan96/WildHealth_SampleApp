package com.example.wildhealth_sampleapp.model.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Workout(
    val bodyPart : String,
    val equipment : String,
    val gifUrl : String,
    val id : String,
    val name : String,
    val target : String
)