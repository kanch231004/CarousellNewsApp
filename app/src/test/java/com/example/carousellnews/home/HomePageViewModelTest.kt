package com.example.carousellnews.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.carousellnews.api.Result
import com.example.carousellnews.data.NewsItem
import com.example.carousellnews.util.getOrAwaitValueTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HomePageViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var homePageViewModel: HomePageViewModel
    private var fakeNewsRepository = mock<HomePageRepository>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun viewmodel_shows_loading_state_for_activity_when_it_is_created() {
        testScope.runTest {
            homePageViewModel = HomePageViewModel(fakeNewsRepository)
            advanceUntilIdle()
            val value = homePageViewModel.uiStateLd.getOrAwaitValueTest()
            assert(value is UIState.Loading)
        }
    }

    @Test
    fun viewModel_updates_on_successful_repository_data_with_live_data_success_and_news_list() {
        val fakeNewsList = listOf<NewsItem>(
            NewsItem(id= "1", title = "TestTitle", description = "Test Description",
                bannerUrl = "xyz", timeCreated = 12445322, rank = 4)
        )
        testScope.runTest {
            Mockito.`when`(fakeNewsRepository.getNews()).thenReturn(Result.Success(fakeNewsList))
            homePageViewModel = HomePageViewModel(fakeNewsRepository)
            advanceUntilIdle()
            val value = homePageViewModel.uiStateLd.getOrAwaitValueTest()
            assert(value is UIState.Success)
            assert((value as UIState.Success).newsList == fakeNewsList)
        }
    }

    @Test
    fun viewModel_call_repository_get_news_correctly() {
        testScope.runTest {
            homePageViewModel = HomePageViewModel(fakeNewsRepository)
            advanceUntilIdle()
            verify(fakeNewsRepository).getNews()
        }
    }
}