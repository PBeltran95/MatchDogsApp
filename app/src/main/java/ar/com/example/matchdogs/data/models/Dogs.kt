package ar.com.example.matchdogs.data.models

import com.google.gson.annotations.SerializedName

data class Dogs(@SerializedName("status") val breed:String,
                @SerializedName("message") val imageUrl: List<String>
                )

data class DogList(
    val results: List<Dogs> = listOf()
)