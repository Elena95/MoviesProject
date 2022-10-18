package com.example.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.example.movies.data.entities.Movies

@HiltViewModel
class MoviesViewModel @Inject constructor( val getUseCaseMovies:GetMoviesUseCase)
 : ViewModel() {

    private val _resultData = MutableLiveData<List<Movies>>()
    val resultData: LiveData<List<Movies>> = _resultData
//Obtencion de datos del modelo y notificacion a la vista de la informacion obtenida
    fun getMovies() {
    //ejecuta peticion mediante coroutina, validando si hay una excepcion
        viewModelScope.launch {
             try {
                 val result = getUseCaseMovies()
                 if (result.isNotEmpty()) {
                     _resultData.postValue(result)
                 }
             }catch (ex:Exception){
                 onFailureGetHeroes(ex)}
        }

    }
//Informacion del error
    fun onFailureGetHeroes(it: Throwable){
        print("Error $it")
    }


}