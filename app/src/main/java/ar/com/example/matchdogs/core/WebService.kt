package ar.com.example.matchdogs.core

import ar.com.example.matchdogs.data.models.Dogs
import retrofit2.http.GET

interface WebService {

    @GET("20")
    suspend fun getDogsByBreed(): Dogs

}

