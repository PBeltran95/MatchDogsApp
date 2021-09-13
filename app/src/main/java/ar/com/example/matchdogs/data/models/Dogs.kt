package ar.com.example.matchdogs.data.models

import com.google.gson.annotations.SerializedName

data class Dogs(@SerializedName("message") val breed:String,
                @SerializedName("status") val imageUrl: List<String>
                )
