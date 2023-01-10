package com.example.jokeapp.ui

sealed class MainAction {
    object LoadJokesFromApi : MainAction()
    data class ShowJokeDelivery(val elementId: Int) : MainAction()
}
