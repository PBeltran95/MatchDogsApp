package ar.com.example.matchdogs.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.com.example.matchdogs.data.models.DogEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDogDataSourceTest:TestCase(){

    private lateinit var db: AppDataBase
    private lateinit var dao: DogDao
    private lateinit var localDatabaseSource: LocalDogDataSource

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.dogDao()
        localDatabaseSource = LocalDogDataSource(dao)
    }

    @Test
    fun testLocalDbSourceSaveAndRead() = runBlocking{
        val dog = DogEntity(1, "https://www.perros.com/content/perros_com/imagenes/upload/152b05-pug_ventana.jpg","Polito", "Pelotita", true )
        localDatabaseSource.saveDog(dog)

        val dogRecovered = localDatabaseSource.getFavoriteDogs()

        assertEquals(dog, dogRecovered[0])
    }



}