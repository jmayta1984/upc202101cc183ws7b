package pe.edu.upc.xtrememovieapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val overview: String,

    @ColumnInfo
    @SerializedName("poster_path")
    val poster: String,

    @ColumnInfo
    @SerializedName("release_date")
    val releaseDate: String,

)