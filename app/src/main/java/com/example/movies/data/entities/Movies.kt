package com.example.movies.data.entities

import com.example.movies.data.Converters
import com.example.movies.data.database.entities.MoviesEntity
import java.io.Serializable

// Data class donde recibiremos la respues de la peticion popularMovies
data class Response(
    val page: Int,
    var results: List<Movies>,
    var total_pages: Int,
    var total_results: Int,
)
// data class donde recibiremos la data de Movies
data class Movies(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path:String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
): Serializable

//Funcion para convertir Movies a MoviesEntity
fun Movies.toDomain() = MoviesEntity(0,adult, backdrop_path, Converters().listToJson(genre_ids), id, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count)
//Funcion para convertir MoviesEntity a Movies
fun MoviesEntity.toDomain() = Movies(adult, backdrop_path, Converters().jsonToList(genre_ids), id, original_language, original_title, overview, popularity, poster_path, release_date, title, video, vote_average, vote_count)