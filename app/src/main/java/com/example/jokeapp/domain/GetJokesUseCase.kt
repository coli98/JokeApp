package com.example.jokeapp.domain

import com.example.jokeapp.data.repository.JokesRepository
import com.example.jokeapp.domain.model.JokeDto
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetJokesUseCase @Inject constructor(
    private val jokesRepository: JokesRepository
) {
    fun getJokes(): Single<List<JokeDto>> =
        jokesRepository.getTwentyJokes().map { jokeList ->
            jokeList.map { joke ->
                JokeDto(
                    setup = joke.setup,
                    delivery = joke.delivery,
                    id = joke.id,
                )
            }.sortedWith(compareBy { it.id })
        }
}
