package com.johnlennonlobo.newstrailers.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnlennonlobo.newstrailers.AppConstant
import com.johnlennonlobo.newstrailers.R
import com.johnlennonlobo.newstrailers.network.model.dto.MovieDTO
import com.squareup.picasso.Picasso
import okhttp3.internal.addHeaderLenient

class MoviesAdapter(private val context: Context, private val movies: List<MovieDTO>)
    : RecyclerView.Adapter<MoviesAdapter.MyMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MyMovieViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) {
        movies[position].apply {
            Picasso.get()
                .load("${AppConstant.TMDB_IMAGE_BASE_URL_W185}${poster_path}")
                .placeholder(R.drawable.placeholder)
                .into(holder.poster)
        }
    }


    override fun getItemCount() = movies.size

    class MyMovieViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var cardView = itemView.findViewById<CardView>(R.id.cv_movie)
        var poster = itemView.findViewById<ImageView>(R.id.iv_movie_poster)
    }
}


