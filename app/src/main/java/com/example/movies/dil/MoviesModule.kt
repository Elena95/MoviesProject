package com.example.movies.dil

import com.example.movies.*
import com.example.movies.data.database.MoviesDataBase
import com.example.movies.data.database.dao.MoviesDao
import com.example.movies.network.NetworkClient
import com.example.movies.repository.Repository
import com.example.movies.repository.RepositoryImpl
import com.example.movies.data.usecaseimp.GetMoviesUsesCaseImp
import com.example.movies.data.usecases.GetMoviesUseCase
import com.example.movies.network.MoviesServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {
//Inyectamos NetworkClient
    @Provides
    fun getServices(): MoviesServices {
        return NetworkClient().getClient()
    }
    //Inyectamos GetMoviesUsesCaseImp
    @Provides
    fun getUseCaseMovies(repository: Repository): GetMoviesUseCase {
        return GetMoviesUsesCaseImp(repository)
    }

//Inyectamos el Repository
    @Provides
    fun getRepository(movieService: MoviesServices, moviesDao: MoviesDao): Repository{
        return RepositoryImpl(movieService, moviesDao)
    }

}


