package pe.edu.upc.moviedemo.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import pe.edu.upc.moviedemo.R
import pe.edu.upc.moviedemo.data.entities.Movie
import pe.edu.upc.moviedemo.data.local.AppDatabase
import pe.edu.upc.moviedemo.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var btSave: Button
    private lateinit var btFavorite: ImageButton
    private lateinit var ivPoster: ImageView
    private lateinit var etReview: EditText

    private lateinit var movie: Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initViews()
        initListeners()
        loadMovie()
    }

    private fun initListeners() {
        btSave.setOnClickListener {
            movie.review = etReview.text.toString()
            AppDatabase.getInstance(this).movieDao().insert(movie)
            finish()
        }

        btFavorite.setOnClickListener {
            if (movie.isFavorite > 0) {
                movie.isFavorite = 0
                btFavorite.setColorFilter(
                    resources.getColor(
                        android.R.color.transparent,
                        theme
                    )
                )
            } else {
                movie.isFavorite = 1
                movie.review = ""
                btFavorite.setColorFilter(
                    this.resources.getColor(
                        R.color.purple_200,
                        theme
                    )
                )
            }
        }
    }

    private fun initViews() {
        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ivPoster = findViewById(R.id.ivPoster)
        btFavorite = findViewById(R.id.btFavorite)
        btSave = findViewById(R.id.btSave)
        etReview = findViewById(R.id.etReview)
    }

    private fun loadMovie() {
        val id = intent.getIntExtra("extraId", 0)
        val apiKey = "3cae426b920b29ed2fb1c0749f258325"

        val movieInterface = ApiClient.build()

        val movieDetail = movieInterface?.movieDetail(id, apiKey)

        movieDetail?.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    movie = response.body()!!
                    tvTitle.text = movie.title
                    tvOverview.text = movie.overview

                    Glide.with(this@MovieDetailActivity)
                        .load(ApiClient.IMAGE_BASE_URL + movie.poster).into(ivPoster)

                    val movieDb =
                        AppDatabase.getInstance(this@MovieDetailActivity).movieDao().findById(id)
                    if (movieDb != null) {
                        movie.isFavorite = 1
                        etReview.setText(movieDb.review)
                        btFavorite.setColorFilter(resources.getColor(R.color.purple_200, theme))
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }

        })


    }

}