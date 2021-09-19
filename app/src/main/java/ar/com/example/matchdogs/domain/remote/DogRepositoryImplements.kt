package ar.com.example.matchdogs.domain.remote

import ar.com.example.matchdogs.data.models.Dogs
import ar.com.example.matchdogs.data.remote.DogDataSource
import javax.inject.Inject

class DogRepositoryImplements @Inject constructor (private val dataSource : DogDataSource) : RepositoryOfDogs {

    override suspend fun getDogs(): Dogs = dataSource.getAllDogs()
}