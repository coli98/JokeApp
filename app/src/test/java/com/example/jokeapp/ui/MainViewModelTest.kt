package com.example.jokeapp.ui

import com.example.jokeapp.domain.GetJokesUseCase
import com.example.jokeapp.domain.model.JokeDto
import com.example.jokeapp.utlis.NetworkConnectionManager
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub


class MainViewModelTest {

    @Mock
    private lateinit var getJokesUseCase: GetJokesUseCase

    private val stateSubject = BehaviorSubject.create<MainState>()

    @Mock
    private lateinit var networkConnectionManager: NetworkConnectionManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun shouldSetListOfJokesWhenListFromApiLoaded() {
        getJokesUseCase.stub {
            on { getJokes() } doReturn Single.just(testJokeList)
        }
        networkConnectionManager.stub {
            on { isNetworkConnectionAvailable() } doReturn true
        }

        stateSubject.onNext(MainState.Loading)
        val viewModel = createSUT()
        val testObserver = viewModel.observeState().test()
        viewModel.onAction(MainAction.LoadJokesFromApi).test()
        testObserver.assertValues(MainState.Loading, MainState.Loaded(testJokeList))
    }

    @Test
    fun shouldSetNoInternetStateWhenDeviceHasNoInternet() {
        networkConnectionManager.stub {
            on { isNetworkConnectionAvailable() } doReturn false
        }
        stateSubject.onNext(MainState.Loading)
        val viewModel = createSUT()
        val testObserver = viewModel.observeState().test()
        viewModel.onAction(MainAction.LoadJokesFromApi).test()
        testObserver.assertValues(MainState.Loading, MainState.NoInternetConnection)
    }

    @Test
    fun shouldSetErrorStateWhenApiCalledFailed() {
        getJokesUseCase.stub {
            on { getJokes() } doReturn Single.error { Throwable(errorText) }
        }
        networkConnectionManager.stub {
            on { isNetworkConnectionAvailable() } doReturn true
        }
        stateSubject.onNext(MainState.Loading)
        val viewModel = createSUT()
        val testObserver = viewModel.observeState().test()
        viewModel.onAction(MainAction.LoadJokesFromApi).test()
        testObserver.assertValueAt(1) { it is MainState.Error }
    }

    @Test
    fun shouldSetLoadingStateWhenApiCalledStarted() {
        getJokesUseCase.stub {
            on { getJokes() } doReturn Single.just(testJokeList)
        }
        networkConnectionManager.stub {
            on { isNetworkConnectionAvailable() } doReturn true
        }

        stateSubject.onNext(MainState.Loading)
        val viewModel = createSUT()
        val testObserver = viewModel.observeState().test()
        viewModel.onAction(MainAction.LoadJokesFromApi).test()
        testObserver.assertValueAt(0) { it == MainState.Loading }
    }

    @Test
    fun shouldShowJokeDeliveryWhenCardClicked() {
        stateSubject.onNext(
            MainState.Loaded(testJokeList)
        )
        val viewModel = createSUT()
        val testObserver = viewModel.observeState().test()
        viewModel.onAction(MainAction.ShowJokeDelivery(0)).test()
        testObserver.assertValues(
            MainState.Loaded(testJokeList),
            MainState.Loaded(testJokeListAfterShowDelivery)
        )
    }

    private fun createSUT() =
        MainViewModel(
            getJokesUseCase,
            stateSubject,
            networkConnectionManager
        )

    companion object {
        private val testJokeList = listOf<JokeDto>(
            JokeDto("test", "test", 0),
            JokeDto("test1", "test1", 1),
            JokeDto("test2", "test2", 2),
            JokeDto("test3", "test3", 3),
        )
        private val testJokeListAfterShowDelivery = listOf<JokeDto>(
            JokeDto("test", "test", 0, true),
            JokeDto("test1", "test1", 1),
            JokeDto("test2", "test2", 2),
            JokeDto("test3", "test3", 3),
        )

        private const val errorText = "Error Text"
    }
}