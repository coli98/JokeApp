package com.example.jokeapp.domain.model

data class JokeDto(
    val setup: String,
    val delivery: String,
    val id: Int,
    var isDeliveryShowed: Boolean = false,
)