package com.example.movies.data.usecases

import com.example.movies.data.entities.Movies

// Definimos las funciones de la interfaz
interface GetMoviesUseCase {
    suspend operator fun invoke(): List<Movies>
}
