package com.example.movies.data.usecaseimp

import com.example.movies.data.entities.Movies
import com.example.movies.data.entities.toDomain
import com.example.movies.data.usecases.GetMoviesUseCase
import com.example.movies.repository.Repository
import javax.inject.Inject

//Implementacion del GetMoviesUseCase, Llamaremos a getMovies del repository para obtener la respuesta del servicio
class GetMoviesUsesCaseImp @Inject constructor(private val repository: Repository) :
    GetMoviesUseCase {

    override suspend fun invoke(): List<Movies> {
        val movies = repository.getMovies()
        //Validamos si la lista tiene informacion, de ser asi limpiamos la tabla Movies, insertamos los campos en la tabla, y retornamos la informacion del servicio
       return  if (movies.isNotEmpty()) {
            repository.clearMovies()
            repository.insertMovies(movies.map { it.toDomain() })
            return movies
        }else
            //De lo contrario se mandara a traer la data almacenada en la Base de datos
            repository.getMoviesFromDataBase().map { it.toDomain() }
        }
    }
