package com.example.jokeapp.data.repository

import com.example.jokeapp.data.model.Joke
import com.example.jokeapp.data.remote.NetworkService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


interface JokesRepository {
    fun getTwentyJokes(): Single<List<Joke>>
}

class JokesRepositoryImpl @Inject constructor(private val networkService: NetworkService) :
    JokesRepository {
    override fun getTwentyJokes(): Single<List<Joke>> = Single.zip(
        networkService.getJokes(), networkService.getJokes()
    ) { jokes1, jokes2 ->
        jokes1.jokes + jokes2.jokes
    }
}




