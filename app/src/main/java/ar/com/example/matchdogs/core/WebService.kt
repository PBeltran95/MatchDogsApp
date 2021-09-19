package ar.com.example.matchdogs.core

import ar.com.example.matchdogs.application.AppConstants
import ar.com.example.matchdogs.data.models.Dogs
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WebService {

    @GET("20")
    suspend fun getDogsByBreed(): Dogs

}

