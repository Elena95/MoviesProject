package com.example.movies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.adapter.MoviesAdapter
import com.example.movies.data.entities.Movies
import com.example.movies.viewmodel.MoviesViewModel
import com.example.movies.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MoviesFragment : Fragment() {
        private lateinit var binding: FragmentMoviesBinding
        private val viewModel: MoviesViewModel by viewModels()
        private lateinit var moviesAdapter: MoviesAdapter
        private lateinit var layoutManager: LinearLayoutManager
        private lateinit var navController: NavController
        private var listResult = mutableListOf<Movies>()
//Inflando El fragmento añadiendo los elementos que se ocuparan, tanto el navController para el direccionamiento y el viewModel
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            binding = FragmentMoviesBinding.inflate(inflater, container, false)
            layoutManager = LinearLayoutManager(context)
            binding.mRecyclerView.layoutManager = layoutManager
            navController = findNavController()
            viewModel.getMovies()
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            moviesAdapter = MoviesAdapter(MoviesAdapter.OnClickListener {
             /*   navController.navigate(
                    HeroesFragmentDirections.actionHeroesFragmentToDetailScreenFragment(
                        it,
                        it.name
                    )
                )*/
            })
            binding.mRecyclerView.adapter = moviesAdapter

            binding.mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                   /* if (isLastItemVisible(layoutManager)) {

                    }*/
                }
            })
            observeViewModel()

        }

    //Comportamiento si la aplicacion entra en onResume
        override fun onResume() {
            super.onResume()
            val distinct = listResult.toSet().toList()
            listResult.clear()
            listResult.addAll(distinct)
        }

    //Notifica al viewModel que se añadira un nuevo elemento a la lista para mostrar en la vista
        private fun observeViewModel() {
            viewModel.resultData.observe(viewLifecycleOwner) {
                it?.let {

                    listResult.addAll(it)
                    if (it.isNotEmpty()) {
                        moviesAdapter.submitList(listResult.toList())
                    }
                }
            }

        }
    }