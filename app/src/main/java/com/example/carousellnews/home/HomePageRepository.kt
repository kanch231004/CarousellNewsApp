package com.example.carousellnews.home

import com.example.carousellnews.api.BaseDataSource
import com.example.carousellnews.api.CarousellNewsService
import com.example.carousellnews.api.Result
import com.example.carousellnews.data.NewsItem
import javax.inject.Inject

class HomePageRepository @Inject constructor(
    private val apiService: CarousellNewsService
): HomePageRepoProvider, BaseDataSource() {
    override suspend fun getNews(): Result<List<NewsItem>> {
        return getResult { apiService.getNews() }
    }
}

/** Interface approach is required for unit testing */
interface HomePageRepoProvider {
    suspend fun getNews(): Result<List<NewsItem>>
}