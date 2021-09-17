package ar.com.example.matchdogs.domain.local

import ar.com.example.matchdogs.data.models.DogEntity

interface LocalDogRepo {

    suspend fun getFavoriteDogs(): List<DogEntity>

    suspend fun saveDog(dog:DogEntity)


}