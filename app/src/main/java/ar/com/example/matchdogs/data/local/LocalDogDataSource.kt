package ar.com.example.matchdogs.data.local

import ar.com.example.matchdogs.data.models.DogEntity
import javax.inject.Inject

class LocalDogDataSource @Inject constructor (private val dogDao: DogDao) {

    suspend fun getFavoriteDogs():List<DogEntity> = dogDao.getAllFavoriteDogs()

    suspend fun saveDog(dog:DogEntity){
        dogDao.saveDog(dog)
    }

}