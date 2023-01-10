package com.example.jokeapp.domain

import com.example.jokeapp.data.repository.JokesRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub
import org.mockito.kotlin.verify

class GetJokesUseCaseTest {

    @Mock
    private lateinit var jokesRepository: JokesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun shouldGetDataFromApiWhenGetJokesInvolved() {
        val useCase = createSUT()
        jokesRepository.stub {
            on { getTwentyJokes() } doReturn Single.just(emptyList())
        }
        useCase.getJokes().test().assertComplete()
        verify(jokesRepository).getTwentyJokes()
    }

    private fun createSUT() = GetJokesUseCase(jokesRepository)
}
