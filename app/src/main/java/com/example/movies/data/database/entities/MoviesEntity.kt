package com.example.movies.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey

//Tabla donde se guardara la informacion que obtendremos del servicio para persistir los datos en modo offline
@Entity(tableName= "movies_table")
data class MoviesEntity  (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idResult") val idResult:Int = 0,
    @ColumnInfo(name= "adult")  val  adult: Boolean,
    @ColumnInfo(name= "backdrop_path") val backdrop_path: String,
    @ColumnInfo(name= "genre_ids") val genre_ids: String,
    @ColumnInfo(name= "id") val id: Int,
    @ColumnInfo(name= "original_language") val original_language: String,
    @ColumnInfo(name= "original_title") val original_title: String,
    @ColumnInfo(name= "overview") val overview: String,
    @ColumnInfo(name= "popularity") val popularity: Float,
    @ColumnInfo(name= "poster_path") val poster_path:String,
    @ColumnInfo(name= "release_date") val release_date: String,
    @ColumnInfo(name= "title") val title: String,
    @ColumnInfo(name= "video") val video: Boolean,
    @ColumnInfo(name= "vote_average") val vote_average: Float,
    @ColumnInfo(name= "vote_count") val vote_count: Int,
)



