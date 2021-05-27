package pe.edu.upc.moviedemo.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.edu.upc.moviedemo.R
import pe.edu.upc.moviedemo.data.entities.Movie
import pe.edu.upc.moviedemo.data.remote.ApiClient
import pe.edu.upc.moviedemo.data.remote.ApiResponse
import pe.edu.upc.moviedemo.ui.search.MovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btSearch: Button
    private lateinit var rvMovie: RecyclerView

    private lateinit var movieAdapter: MovieAdapter
    private var movies: MutableList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()
    }

    private fun initListeners() {
        btSearch.setOnClickListener {
            searchMovies()
        }
    }

    private fun searchMovies() {
        val apiKey = "3cae426b920b29ed2fb1c0749f258325"
        val query = etSearch.text.toString()

        val movieInterface = ApiClient.build()
        val searchMovies = movieInterface?.searchMovies(apiKey, query)

        searchMovies?.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

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

    private fun initViews() {
        etSearch = findViewById(R.id.etSearch)
        btSearch = findViewById(R.id.btSearch)
        rvMovie = findViewById(R.id.rvMovie)

        movieAdapter = MovieAdapter(this, movies)
        rvMovie.adapter = movieAdapter
        rvMovie.layoutManager = LinearLayoutManager(this)
    }
}
