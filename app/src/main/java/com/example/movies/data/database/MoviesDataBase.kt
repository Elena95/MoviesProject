package com.example.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.database.dao.MoviesDao
import com.example.movies.data.database.entities.MoviesEntity
//Creación de la base de datos
@Database(entities = [MoviesEntity::class], version =1)
abstract class MoviesDataBase: RoomDatabase() {
    abstract fun getMoviesDao():MoviesDao
}