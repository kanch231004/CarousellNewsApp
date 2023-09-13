package com.example.carousellnews.di

import com.example.carousellnews.api.CarousellNewsService
import com.example.carousellnews.api.RetrofitFactory
import com.example.carousellnews.home.HomePageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun providesAPIService(): CarousellNewsService = RetrofitFactory.getService()
    @Provides
    @Singleton
    fun providesForYouRepository(apiService: CarousellNewsService): HomePageRepository = HomePageRepository(apiService)
}