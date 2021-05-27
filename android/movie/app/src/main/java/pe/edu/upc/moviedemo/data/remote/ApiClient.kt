package pe.edu.upc.moviedemo.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val API_BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    private var movieInterface: MovieInterface? = null

    fun build(): MovieInterface? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        var retrofit: Retrofit = builder.build()
        movieInterface = retrofit.create(
            MovieInterface::class.java
        )
        return movieInterface as MovieInterface
    }
}