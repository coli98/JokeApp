package com.example.jokeapp.di

import com.example.jokeapp.data.repository.JokesRepository
import com.example.jokeapp.data.repository.JokesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUserRepository(impl: JokesRepositoryImpl): JokesRepository
}