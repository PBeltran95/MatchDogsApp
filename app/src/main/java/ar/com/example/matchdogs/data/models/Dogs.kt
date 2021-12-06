package ar.com.example.matchdogs.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Dogs(@SerializedName("status") val breed:String,
                @SerializedName("message") val imageUrl: List<String>
                )


@Entity
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int =  0,
    var imageUrl : String = "",
    var name:String = "",
    var game:String = "",
    var isFavorite: Boolean = false
)
