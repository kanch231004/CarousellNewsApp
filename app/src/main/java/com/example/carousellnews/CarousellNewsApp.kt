package com.example.carousellnews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarousellNewsApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}