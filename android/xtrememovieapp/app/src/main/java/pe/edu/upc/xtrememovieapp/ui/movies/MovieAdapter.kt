package pe.edu.upc.xtrememovieapp.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.upc.xtrememovieapp.R
import pe.edu.upc.xtrememovieapp.data.entities.Movie
import pe.edu.upc.xtrememovieapp.data.local.AppDatabase
import pe.edu.upc.xtrememovieapp.data.remote.ApiClient

class MovieAdapter(private val context: Context, private var movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MoviePrototype>() {

    inner class MoviePrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvTitle: TextView
        private lateinit var tvReleaseDate: TextView
        private lateinit var ivPoster: ImageView
        private lateinit var btFavorite: ImageButton

        fun bindTo(movie: Movie) {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate)
            ivPoster = itemView.findViewById(R.id.ivPoster)
            tvTitle.text = movie.title
            tvReleaseDate.text = movie.overview
            Glide.with(context).load(ApiClient.IMAGE_BASE_URL + movie.poster).into(ivPoster)

            btFavorite = itemView.findViewById(R.id.btFavorite)
            if (AppDatabase.getInstance(context).movieDao().findById(movie.id).size > 0) {
                btFavorite.setColorFilter(
                    context.resources.getColor(
                        R.color.purple_200,
                        context.theme
                    )
                )
            } else {
                btFavorite.setColorFilter(
                    context.resources.getColor(
                        android.R.color.transparent,
                        context.theme
                    )
                )
            }

            btFavorite.setOnClickListener {
                val results = AppDatabase.getInstance(context).movieDao().findById(movie.id)
                if (results.size > 0) {
                    AppDatabase.getInstance(context).movieDao().delete(movie)
                    btFavorite.setColorFilter(
                        context.resources.getColor(
                            android.R.color.transparent,
                            context.theme
                        )
                    )
                } else {
                    AppDatabase.getInstance(context).movieDao().insert(movie)
                    btFavorite.setColorFilter(
                        context.resources.getColor(
                            R.color.purple_200,
                            context.theme
                        )
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePrototype {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_movie, parent, false)
        return MoviePrototype(view)
    }

    override fun onBindViewHolder(prototype: MoviePrototype, position: Int) {
        prototype.bindTo(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setItems(movies: MutableList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
}


