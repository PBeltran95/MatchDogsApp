package ar.com.example.matchdogs.data.remote

import ar.com.example.matchdogs.core.WebService
import ar.com.example.matchdogs.data.models.Dogs
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogDataSource @Inject constructor (private val webService: WebService) {

    suspend fun getAllDogs(): Dogs = webService.getDogsByBreed()

}