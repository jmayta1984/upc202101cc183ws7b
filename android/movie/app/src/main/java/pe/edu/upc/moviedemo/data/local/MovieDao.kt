package pe.edu.upc.moviedemo.data.local

import androidx.room.*
import pe.edu.upc.moviedemo.data.entities.Movie

@Dao
interface MovieDao {

    @Query("select * from movie")
    fun getAll(): MutableList<Movie>

    @Query("select * from movie where id = :id")
    fun findById(id: Int): Movie

    @Delete
    fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movie: Movie)

    @Update
    fun update(vararg movie: Movie)

}