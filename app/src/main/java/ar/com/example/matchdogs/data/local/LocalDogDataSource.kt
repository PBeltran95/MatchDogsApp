package ar.com.example.matchdogs.data.local

import androidx.lifecycle.LiveData
import ar.com.example.matchdogs.data.models.DogEntity

class LocalDogDataSource(private val dogDao: DogDao) {

    suspend fun getFavoriteDogs():List<DogEntity> = dogDao.getAllFavoriteDogs()

    suspend fun saveDog(dog:DogEntity){
        dogDao.saveDog(dog)
    }

}