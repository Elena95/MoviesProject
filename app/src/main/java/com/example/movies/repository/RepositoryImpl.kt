package com.example.movies.repository

import com.example.movies.data.database.dao.MoviesDao
import com.example.movies.data.database.entities.MoviesEntity
import com.example.movies.data.entities.Movies
import com.example.movies.network.MoviesServices
import javax.inject.Inject

open class RepositoryImpl @Inject constructor(
    private val api : MoviesServices,
    private val moviesDao:MoviesDao) : Repository {
//Obteniendo la api_key para la peticion
    private val apikey = com.example.movies.BuildConfig.API_KEY
    //Se manda a ejecutar el servicio para obtener las peliculas, cachando si existe alguna excepcion
    override suspend fun getMovies(): List<Movies>{
        return try{
            var response=api.popularMovies(apikey).results
            if(response.isEmpty()) {
                response= emptyList()
            }
            response

        }catch(ex: Exception){
            ex.printStackTrace()
            val list: List<Movies>
            list = mutableListOf()
            list
        }




    }
   // override suspend fun getMovies(): List<Movies> = api.popularMovies(apikey).results

    override suspend fun getMoviesFromDataBase(): List<MoviesEntity> = moviesDao.getAllMovies()

    override suspend fun insertMovies(movies: List<MoviesEntity>) {
        moviesDao.insertAll(movies)
    }


    override suspend fun clearMovies() {
moviesDao.clearMovies()    }
}