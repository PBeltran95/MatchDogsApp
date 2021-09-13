package ar.com.example.matchdogs.domain.remote

import ar.com.example.matchdogs.data.models.DogList
import ar.com.example.matchdogs.data.models.Dogs
import ar.com.example.matchdogs.data.remote.DogDataSource

class DogRepositoryImplements(private val dataSource : DogDataSource) : RepositoryOfDogs {

    override suspend fun getDogs(breed:String): Dogs = dataSource.getAllDogs(breed)
}