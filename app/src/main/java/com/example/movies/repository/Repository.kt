package com.example.movies.repository


import com.example.movies.data.database.entities.MoviesEntity
import com.example.movies.data.entities.Movies
import com.example.movies.data.entities.Response

//Definiendo la interfaz del repositorio
interface Repository {
    suspend fun getMovies(): List<Movies>
    suspend fun getMoviesFromDataBase():List<MoviesEntity>
    suspend fun insertMovies(movies: List<MoviesEntity>)
    suspend fun clearMovies()

}