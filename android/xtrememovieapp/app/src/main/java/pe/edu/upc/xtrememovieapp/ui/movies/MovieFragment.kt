package pe.edu.upc.xtrememovieapp.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.xtrememovieapp.R
import pe.edu.upc.xtrememovieapp.data.entities.Movie
import pe.edu.upc.xtrememovieapp.data.remote.ApiClient
import pe.edu.upc.xtrememovieapp.data.remote.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {
    private lateinit var rvMovie: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private var movies: MutableList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.url_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemPopular -> {
                urlMovies(ApiClient.POPULAR_URL)
                true
            }
            R.id.itemUpcoming -> {
                urlMovies(ApiClient.UPCOMING_URL)
                true
            }
            R.id.itemTopRated -> {
                urlMovies(ApiClient.TOP_RATED_URL)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovie = view.findViewById(R.id.rvMovie)
        movieAdapter = MovieAdapter(view.context, movies)
        rvMovie.adapter = movieAdapter
        rvMovie.layoutManager = LinearLayoutManager(view.context)
        urlMovies(ApiClient.POPULAR_URL)
    }

    private fun urlMovies(url: String) {
        val movieInterface = ApiClient.build()
        val popularMovies = movieInterface?.popularMovies(url, ApiClient.API_KEY)
        popularMovies?.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("MovieFragment", t.toString())
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    movies = response.body()!!.results
                    movieAdapter.setItems(movies)
                }
            }
        }
        )
    }

}