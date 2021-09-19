package ar.com.example.matchdogs.domain.remote

import ar.com.example.matchdogs.data.models.Dogs

interface RepositoryOfDogs {

    suspend fun getDogs():Dogs

}