package ar.com.example.matchdogs.data.local

import androidx.room.*
import ar.com.example.matchdogs.data.models.DogEntity

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDog(dog:DogEntity)

    @Query("SELECT * FROM dogentity")
    suspend fun getAllFavoriteDogs():List<DogEntity>

    @Delete
    suspend fun deleteSelectedDog(dog: DogEntity)




}