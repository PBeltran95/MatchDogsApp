package ar.com.example.matchdogs.data.local

import ar.com.example.matchdogs.data.models.DogEntity

class LocalDogDataSource(private val dogDao: DogDao) {

    suspend fun getFavoriteDogs():MutableList<DogEntity> = dogDao.getAllFavoriteDogs()

    suspend fun saveDog(dog:DogEntity){
        dogDao.saveDog(dog)
    }

}