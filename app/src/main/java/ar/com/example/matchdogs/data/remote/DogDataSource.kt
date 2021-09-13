package ar.com.example.matchdogs.data.remote

import ar.com.example.matchdogs.core.WebService
import ar.com.example.matchdogs.data.models.DogList
import ar.com.example.matchdogs.data.models.Dogs
import retrofit2.Retrofit

class DogDataSource(private val webService: WebService) {

    suspend fun getAllDogs(breed:String): Dogs = webService.getDogsByBreed("$breed/images")

}