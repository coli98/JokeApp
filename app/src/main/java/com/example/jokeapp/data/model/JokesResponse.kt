package com.example.jokeapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokesResponse(
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "error")
    val error: Boolean,
    @Json(name = "jokes")
    val jokes: List<Joke>
)