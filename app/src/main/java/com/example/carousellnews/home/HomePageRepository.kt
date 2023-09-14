package com.example.carousellnews.home

import com.example.carousellnews.api.CarousellNewsService
import com.example.carousellnews.api.Result
import com.example.carousellnews.api.RetrofitFactory
import com.example.carousellnews.data.NewsItem
import javax.inject.Inject

class HomePageRepository @Inject constructor(private val apiService: CarousellNewsService): HomePageRepoProvider {
    override suspend fun getNews(): Result<List<NewsItem>> {
        /** Response classification of success and error can be done at generic place */
         val result = apiService.getNews()
        return if (result.isSuccessful) {
            val body = result.body() as List<NewsItem>
            Result.Success(body)
        } else {
            val errorBody = result.errorBody().toString()
            Result.Error(errorBody)
        }
    }
}


/** Interface approach is required for unit testing */
interface HomePageRepoProvider {
    suspend fun getNews(): Result<List<NewsItem>>
}