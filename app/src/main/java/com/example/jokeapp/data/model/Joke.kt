package com.example.jokeapp.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Joke(
    @Json(name = "category")
    val category: String,
    @Json(name = "delivery")
    val delivery: String,
    @Json(name = "flags")
    val flags: Flags,
    @Json(name = "id")
    val id: Int,
    @Json(name = "lang")
    val lang: String,
    @Json(name = "safe")
    val safe: Boolean,
    @Json(name = "setup")
    val setup: String,
    @Json(name = "type")
    val type: String
)