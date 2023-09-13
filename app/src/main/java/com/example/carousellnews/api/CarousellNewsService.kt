package com.example.carousellnews.api

import com.example.carousellnews.data.NewsItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitFactory {
    private val okHttpClient =  OkHttpClient.Builder().addNetworkInterceptor(
        HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger {
            }).setLevel(HttpLoggingInterceptor.Level.BASIC))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://storage.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getService() = retrofit.create(CarousellNewsService::class.java)
}

interface CarousellNewsService {
    @GET("/carousell-interview-assets/android/carousell_news.json")
    suspend fun getNews(): Response<List<NewsItem>>
}