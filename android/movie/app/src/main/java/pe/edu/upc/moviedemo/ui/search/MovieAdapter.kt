package pe.edu.upc.moviedemo.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.upc.moviedemo.R
import pe.edu.upc.moviedemo.data.entities.Movie
import pe.edu.upc.moviedemo.data.local.AppDatabase
import pe.edu.upc.moviedemo.data.remote.ApiClient
import pe.edu.upc.moviedemo.ui.detail.MovieDetailActivity

class MovieAdapter(private val context: Context, private var movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MoviePrototype>() {

    inner class MoviePrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var tvTitle: TextView
        private lateinit var tvOverview: TextView
        private lateinit var ivPoster: ImageView
        private lateinit var btFavorite: ImageButton
        private lateinit var cvMovie: CardView

        fun bindTo(movie: Movie) {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvOverview = itemView.findViewById(R.id.tvOverview)
            ivPoster = itemView.findViewById(R.id.ivPoster)
            btFavorite = itemView.findViewById(R.id.btFavorite)
            cvMovie = itemView.findViewById(R.id.cvMovie)

            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            Glide.with(context).load(ApiClient.IMAGE_BASE_URL + movie.poster).into(ivPoster)

            if (AppDatabase.getInstance(context).movieDao().findById(movie.id) != null)
                movie.isFavorite = 1

            if (movie.isFavorite > 0) {
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

                if (movie.isFavorite > 0) {
                    movie.isFavorite = 0
                    AppDatabase.getInstance(context).movieDao().delete(movie)
                    btFavorite.setColorFilter(
                        context.resources.getColor(
                            android.R.color.transparent,
                            context.theme
                        )
                    )
                } else {
                    movie.isFavorite = 1
                    movie.review = ""
                    AppDatabase.getInstance(context).movieDao().insert(movie)
                    btFavorite.setColorFilter(
                        context.resources.getColor(
                            R.color.purple_200,
                            context.theme
                        )
                    )
                }


            }

            cvMovie.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("extraId", movie.id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePrototype {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_movie, parent, false)
        return MoviePrototype(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(prototype: MoviePrototype, position: Int) {
        prototype.bindTo(movies[position])
    }

    fun setItems(movies: MutableList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

}
