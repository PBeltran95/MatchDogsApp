package ar.com.example.matchdogs.core

import ar.com.example.matchdogs.application.AppConstants
import ar.com.example.matchdogs.data.models.Dogs
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface WebService {

    @GET
    suspend fun getDogsByBreed(@Url url:String): Dogs

}


object RetrofitClient{

    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }




}