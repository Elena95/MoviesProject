package com.example.movies.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movies.Common.charactersPOJOModel
import com.example.movies.repository.Repository
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.mock


class GetHeroesUsesCaseTest : TestCase() {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    private val mockRepository = mock(Repository::class.java)
    val UseCase by lazy { GetHeroesUsesCaseImp(mockRepository) }

    @Test
    fun testHeroesUseCases_getHeroes_Completed() {
        val response = charactersPOJOModel()
        Mockito.`when`(mockRepository.getCharacters(anyInt(), anyInt()))
            .thenReturn(Single.just(response))
        UseCase(0, 2)
            .test()
            .assertComplete()
    }

   /* @Test
    fun testHeroesUseCases_getHeroes_Error() {

        val response = Throwable("Error")

         Mockito.`when`(mockRepository.getCharacters(anyInt(), anyInt()))
             .thenReturn(throw response)

    }*/

    @Test
    fun testHeroesUseCases_getHeroes_response() {
        val response = charactersPOJOModel()
        Mockito.`when`(mockRepository.getCharacters(anyInt(), 0))
            .thenReturn(Single.just(response))
        UseCase(0, 2)
            .test()
            .assertValue(response)
    }

}