package pe.edu.upc.xtrememovieapp.data.local

import androidx.room.*
import pe.edu.upc.xtrememovieapp.data.entities.Movie

@Dao
interface MovieDao {

    @Query("select * from movie")
    fun getAll(): MutableList<Movie>

    @Query("select * from movie where id = :id")
    fun findById(id: Int): MutableList<Movie>

    @Delete
    fun delete(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg movie: Movie)

    @Update
    fun update(vararg movie: Movie)

}