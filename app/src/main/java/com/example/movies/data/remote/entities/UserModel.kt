package com.example.movies.data.remote.entities

//data class para obtener las ubicaciones por usuarip
data class UserModel(
    val empId:String,
    var listUbicaciones: MutableList<LocationModel>?,
    var color: Int )
