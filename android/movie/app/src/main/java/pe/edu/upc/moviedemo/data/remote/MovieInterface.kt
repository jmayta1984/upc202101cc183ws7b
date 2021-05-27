package pe.edu.upc.moviedemo.data.remote

import pe.edu.upc.moviedemo.data.entities.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<ApiResponse>

    @GET("movie/{id}")
    fun movieDetail(@Path("id") id: Int, @Query("api_key") query: String): Call<Movie>
}