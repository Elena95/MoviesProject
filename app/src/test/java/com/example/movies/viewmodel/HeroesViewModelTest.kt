package com.example.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.movies.Common.charactersPOJOModel
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class HeroesViewModelTest : TestCase() {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    //val observerState:Observer<Result>=mock()
    //val observerState2 =mock(Observer<Result>::class.java)

    val getHeroesUsesCase = mock(GetHeroesUsesCase::class.java)
    private val viewmodel by lazy { HeroesViewModel(getHeroesUsesCase) }



    @Test
    fun testHeroesUseCases_updateResultData() {
        val response = charactersPOJOModel()
        Mockito.`when`(viewmodel.getHeroesUsesCase(anyInt(),anyInt()))
            .thenReturn(Single.just(response))

        //viewmodel.resultData.observeForever(observerState)
        //viewmodel.updateCryptoList()

        viewmodel.getHeroesUsesCase(0,2)
            .test()
            .assertValue(response)

    }

}