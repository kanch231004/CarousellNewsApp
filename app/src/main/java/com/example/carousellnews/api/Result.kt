package com.example.carousellnews.api

sealed class Result<out T> {
    data class Success<out T> (val body: T ): Result<T>()
    data class Error <out T>(val errorBody: String?): Result<T>()
}