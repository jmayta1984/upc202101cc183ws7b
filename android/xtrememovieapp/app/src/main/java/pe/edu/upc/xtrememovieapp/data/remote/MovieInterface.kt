package pe.edu.upc.xtrememovieapp.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {
    @GET("{url}")
    fun popularMovies(
        @Path("url") url: String,
        @Query("api_key") apiKey: String
    ): Call<ApiResponse>
}