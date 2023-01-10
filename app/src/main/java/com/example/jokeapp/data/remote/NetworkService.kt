package com.example.jokeapp.data.remote

import com.example.jokeapp.data.model.JokesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET


interface NetworkService {

    /**
     * API return maximal 10 record per call (amount parameter has range from 1 to 10)
     * */
    @GET("/joke/Any?blacklistFlags=nsfw&type=twopart&amount=10")
    fun getJokes(): Single<JokesResponse>
}