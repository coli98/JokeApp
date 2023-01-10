package com.example.jokeapp.ui

import com.example.jokeapp.domain.model.JokeDto

sealed class MainState {
    object NoInternetConnection : MainState()
    data class Error(val errorMessage: String) : MainState()
    object Loading : MainState()
    data class Loaded(
        val jokeList: List<JokeDto> = emptyList(),
    ) : MainState()
}

