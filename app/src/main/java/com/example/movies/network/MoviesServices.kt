package com.example.movies.network

import com.example.movies.data.entities.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Peticiones que se ocuparan
interface MoviesServices {
    @GET("movie/popular")
    suspend fun popularMovies(
        @Query("api_key") apikey: String,
    ): Response
}