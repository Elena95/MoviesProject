package com.example.movies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
//Primer clase que se ejecuta cuando ejecutas la app, y siempre se mantiene viva
@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}