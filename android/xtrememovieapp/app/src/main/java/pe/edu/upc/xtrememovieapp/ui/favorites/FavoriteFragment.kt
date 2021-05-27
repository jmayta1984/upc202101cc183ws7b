package pe.edu.upc.xtrememovieapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.xtrememovieapp.R
import pe.edu.upc.xtrememovieapp.data.entities.Movie
import pe.edu.upc.xtrememovieapp.data.local.AppDatabase

class FavoriteFragment : Fragment() {
    private lateinit var rvFavorite: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var movies: MutableList<Movie> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFavorite = view.findViewById(R.id.rvFavorite)
        favoriteAdapter = FavoriteAdapter(view.context, movies)
        rvFavorite.adapter = favoriteAdapter
        rvFavorite.layoutManager = LinearLayoutManager(view.context)
        favoriteMovies()
    }

    private fun favoriteMovies() {
        movies = AppDatabase.getInstance(requireContext()).movieDao().getAll()
        favoriteAdapter.setItems(movies)
    }

}

