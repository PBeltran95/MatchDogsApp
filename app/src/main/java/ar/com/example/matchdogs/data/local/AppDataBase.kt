package ar.com.example.matchdogs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.com.example.matchdogs.data.models.DogEntity

@Database(entities = [DogEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun dogDao(): DogDao

}