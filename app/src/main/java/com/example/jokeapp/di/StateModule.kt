package com.example.jokeapp.di

import com.example.jokeapp.ui.MainState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.subjects.BehaviorSubject

@Module
@InstallIn(ViewModelComponent::class)
object StateModule {

    @Provides
    @ViewModelScoped
    fun provideStateToLogin(): BehaviorSubject<@JvmSuppressWildcards MainState> =
        BehaviorSubject.createDefault(
            MainState.Loading
        )
}