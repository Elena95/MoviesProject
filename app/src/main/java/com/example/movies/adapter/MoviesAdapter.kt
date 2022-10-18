package com.example.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.ItemMoviesBinding
import com.example.movies.data.entities.Movies


class MoviesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Movies, MoviesAdapter.MoviesViewHolder>(
        HEROESCOMPARATOR
    ) {
    //Verifico que no contenga repetidos mi objeto Movies
    object HEROESCOMPARATOR : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }

    }
//Creo mi ViewHolder inflando el item que se llenara con la informacion del modelo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    //Obtengo la posicion del item que se va a seleccionar
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.onBind(getItem(position))
    }

    class MoviesViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
//Se llena el item con la informacion de la lista obtenida de la peticion
        fun onBind(movie: Movies) = with(binding) {
            tvName.text = movie.title
            tvPercent.text = movie.popularity.toString()
            tvDate.text = movie.release_date
    val path = com.example.movies.BuildConfig.IMAGE_URL+movie.poster_path
            Glide.with(binding.root.context)
                .load(path)
                .into(image)
        }
    }

    class OnClickListener(val clickListener: (result: Movies) -> Unit) {
        fun onClick(result: Movies) = clickListener(result)
    }


}