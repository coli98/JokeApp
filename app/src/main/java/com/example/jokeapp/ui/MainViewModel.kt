package com.example.jokeapp.ui

import androidx.lifecycle.ViewModel
import com.example.jokeapp.domain.GetJokesUseCase
import com.example.jokeapp.utlis.NetworkConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getJokesUseCase: GetJokesUseCase,
    private val stateSubject: BehaviorSubject<@JvmSuppressWildcards MainState>,
    private val networkConnectionManager: NetworkConnectionManager,
) : ViewModel() {

    fun observeState(): Observable<MainState> = stateSubject.distinctUntilChanged().hide()

    fun onAction(action: MainAction): Completable = when (action) {
        MainAction.LoadJokesFromApi -> onLoadJokesFromApi()
        is MainAction.ShowJokeDelivery -> onShowDelivery(action.elementId)
    }

    private fun onShowDelivery(elementId: Int) = Completable.fromAction {
        val oldState = stateSubject.value as MainState.Loaded
        val oldList = oldState.jokeList
        val newElement = oldList[elementId]
            .copy(isDeliveryShowed = oldState.jokeList[elementId].isDeliveryShowed.not())
        val newList = oldList.toMutableList()
        newList[elementId] = newElement
        stateSubject.onNext(oldState.copy(jokeList = newList))
    }

    private fun onLoadJokesFromApi(): Completable =
        if (networkConnectionManager.isNetworkConnectionAvailable()) {
            getJokesUseCase.getJokes().doOnSubscribe {
                stateSubject.onNext(MainState.Loading)
            }.flatMapCompletable { jokesList ->
                Completable.fromAction {
                    stateSubject.onNext(MainState.Loaded(jokesList))
                }
            }.onErrorResumeNext { throwable: Throwable ->
                Completable.fromAction {
                    stateSubject.onNext(MainState.Error(throwable.toString()))
                }
            }
        } else {
            Completable.fromAction {
                stateSubject.onNext(MainState.NoInternetConnection)
            }
        }
}
