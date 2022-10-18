package com.example.movies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.mockito.Mockito.mock
//import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class SearchViewModelTest : TestCase(){
   // private val coroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchFragment

    private val searchUseCase = mock(GetnameStartsWithUsesCase::class.java)


    /*private var DATA_MODEL = DataModel(0, 0, 0, 0, emptyList())

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        searchViewModel = SearchFragment(searchUseCase, coroutineDispatcher)
    }

    companion object {
        val EMPTY_STRING = ""
        val ZERO_VALUE = 0
        val ERROR_MESSAGE = "Error"
        val ERROR_CODE = 502
        val OFFSET_CONFIG = 20
    }

    @Test
    fun fetch_search_characters_with_dataState_succefull() = coroutineDispatcher.runBlockingTest {
        whenever(searchUseCase.searchCharacters(EMPTY_STRING, ZERO_VALUE)).thenReturn(
            flowOf(DataStates.Success(DATA_MODEL))
        )
        searchViewModel.getCharacters()
        coroutineDispatcher.advanceUntilIdle()
        val data = searchViewModel.characters.value

        assertTrue(
            data is DataStates.Success
        )

        val result = data as DataStates.Success

        assertTrue(
            result.data != null
        )
    }

    @Test
    fun fetch_search_characters_with_dataState_error() = coroutineDispatcher.runBlockingTest {
        whenever(searchUseCase.searchCharacters(EMPTY_STRING, ZERO_VALUE)).thenReturn(
            flowOf(DataStates.Error(ERROR_CODE, ERROR_MESSAGE))
        )
        searchViewModel.getCharacters()
        coroutineDispatcher.advanceUntilIdle()
        val data = searchViewModel.characters.value

        assertTrue(
            data is DataStates.Error
        )

        val result = data as DataStates.Error

        assertTrue(
            result.errorMessage.isNotEmpty()
        )

        assertTrue(
            result.code > ZERO_VALUE
        )
    }

    @Test
    fun fetch_search_characters_with_offset_20() = coroutineDispatcher.runBlockingTest {
        DATA_MODEL.offset = OFFSET_CONFIG
        whenever(searchUseCase.searchCharacters(EMPTY_STRING, OFFSET_CONFIG)).thenReturn(
            flowOf(DataStates.Success(DATA_MODEL))
        )
        searchViewModel.nextPage()
        coroutineDispatcher.advanceUntilIdle()
        val data = searchViewModel.characters.value
        assertTrue(
            data is DataStates.Success
        )
        val response = data as DataStates.Success

        Assert.assertEquals(
            DATA_MODEL,
            response.data
        )
    }

    @After
    fun shutDown() {
        Dispatchers.resetMain()
    }*/
}

