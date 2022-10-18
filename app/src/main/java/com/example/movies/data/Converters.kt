package com.example.movies.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
//Funciones para convertir un listado de Int a String para almacenar en la Base de datos la informacion de genre_ids
    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()
}