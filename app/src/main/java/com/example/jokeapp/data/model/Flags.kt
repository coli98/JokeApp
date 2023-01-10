package com.example.jokeapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Flags(
    @Json(name = "explicit")
    val explicit: Boolean,
    @Json(name = "nsfw")
    val nsfw: Boolean,
    @Json(name = "political")
    val political: Boolean,
    @Json(name = "racist")
    val racist: Boolean,
    @Json(name = "religious")
    val religious: Boolean,
    @Json(name = "sexist")
    val sexist: Boolean
)