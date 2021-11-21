package com.johnlennonlobo.newstrailers.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnlennonlobo.newstrailers.R
import com.johnlennonlobo.newstrailers.network.model.dto.MovieDTO

class ListOfMoviesAdapter(private val context: Context, private val listMovies: List<List<MovieDTO>>)
    : RecyclerView.Adapter<ListOfMoviesAdapter.MyListMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListMoviesViewHolder {
       val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_list_rv_movies, parent, false)
       return MyListMoviesViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyListMoviesViewHolder, position: Int) {
        holder.title.text = when(position){
            0 -> "Trending"
            1 -> "UpComming"
            2 -> "Popular"
            3 -> "Top Rated"
            else -> "Movies"
        }

        holder.movies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.movies.adapter = MoviesAdapter(context, listMovies[position])

    }

    override fun getItemCount() = listMovies.size

    class MyListMoviesViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var title  = itemView.findViewById<TextView>(R.id.tv_movies_title)
        var movies = itemView.findViewById<RecyclerView>(R.id.rv_movies)

    }

    }






