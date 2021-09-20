package ar.com.example.matchdogs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ar.com.example.matchdogs.data.models.DogEntity

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDog(dog:DogEntity)

    @Query("SELECT * FROM dogentity")
    suspend fun getAllFavoriteDogs():List<DogEntity>

}