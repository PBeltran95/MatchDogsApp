package ar.com.example.matchdogs.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Dogs(@SerializedName("status") val breed:String,
                @SerializedName("message") val imageUrl: List<String>
                )


@Entity
data class DogEntity(
    @PrimaryKey
    val id: Int = -1,
    val imageUrl : String = "",
    val name:String = "",
    val description:String = "",
    val isFavorite: Boolean = false
)