package pe.edu.upc.xtrememovieapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val API_BASE_URL = "https://api.themoviedb.org/3/movie/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val POPULAR_URL = "popular"
    const val UPCOMING_URL = "upcoming"
    const val TOP_RATED_URL = "top_rated"
    const val API_KEY = "3cae426b920b29ed2fb1c0749f258325"

    private var movieInterface: MovieInterface? = null

    fun build(): MovieInterface? {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.build()
        movieInterface = retrofit.create(
            MovieInterface::class.java
        )
        return movieInterface as MovieInterface
    }
}