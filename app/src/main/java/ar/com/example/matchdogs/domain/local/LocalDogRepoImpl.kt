package ar.com.example.matchdogs.domain.local

import ar.com.example.matchdogs.data.local.LocalDogDataSource
import ar.com.example.matchdogs.data.models.DogEntity

class LocalDogRepoImpl(private val localDataSource: LocalDogDataSource):LocalDogRepo {

    override suspend fun getFavoriteDogs(): List<DogEntity> {
        return localDataSource.getFavoriteDogs()
    }

    override suspend fun saveDog(dog: DogEntity) {
        localDataSource.saveDog(dog)
    }
}