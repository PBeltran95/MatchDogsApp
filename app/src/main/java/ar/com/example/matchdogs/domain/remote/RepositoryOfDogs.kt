package ar.com.example.matchdogs.domain.remote

import ar.com.example.matchdogs.data.models.DogList
import ar.com.example.matchdogs.data.models.Dogs

interface RepositoryOfDogs {

    suspend fun getDogs(breed:String):Dogs

}