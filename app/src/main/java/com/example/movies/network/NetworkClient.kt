package com.example.movies.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//Consumo de API con retrofit
class NetworkClient{
    private val  api = Retrofit.Builder()
        .baseUrl(com.example.movies.BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    //Funcion para obtener el cliente en este caso mi MoviesServices
    fun getClient(): MoviesServices {
        return api
            .build()
            .create(MoviesServices::class.java)
    }

}