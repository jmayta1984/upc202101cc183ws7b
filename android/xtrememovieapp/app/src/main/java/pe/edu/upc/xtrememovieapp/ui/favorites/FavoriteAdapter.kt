package pe.edu.upc.xtrememovieapp.ui.favorites

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

class FavoriteAdapter(private val context: Context, private var movies: MutableList<Movie>) :
    RecyclerView.Adapter<FavoriteAdapter.MoviePrototype>() {

    inner class MoviePrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvTitle: TextView
        private lateinit var tvReleaseDate: TextView
        private lateinit var ivPoster: ImageView
        private lateinit var btDelete: ImageButton

        fun bindTo(movie: Movie) {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate)
            ivPoster = itemView.findViewById(R.id.ivPoster)
            tvTitle.text = movie.title
            tvReleaseDate.text = movie.overview
            Glide.with(context).load(ApiClient.IMAGE_BASE_URL + movie.poster).into(ivPoster)

            btDelete = itemView.findViewById(R.id.btDelete)
            btDelete.setOnClickListener {
                AppDatabase.getInstance(context).movieDao().delete(movie)
                movies.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePrototype {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_favorite, parent, false)
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


