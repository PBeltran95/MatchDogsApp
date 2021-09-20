package ar.com.example.matchdogs.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import ar.com.example.matchdogs.data.models.DogEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppDataBaseTest : TestCase(){

    private lateinit var db: AppDataBase
    private lateinit var dao: DogDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.dogDao()
    }


    @Test
    fun writeAndReadDogs() = runBlocking {
        val dog = DogEntity(1, "https://www.perros.com/content/perros_com/imagenes/upload/152b05-pug_ventana.jpg","Polito", "Pelotita", true )
        dao.saveDog(dog)

        val recoveredDogsFromDb = dao.getAllFavoriteDogs()

        Assert.assertEquals(true, recoveredDogsFromDb.contains(dog))
    }

    @Test
    fun testDatabasePathIsMemory() = runBlocking {

        Assert.assertEquals(":memory:", db.openHelper.readableDatabase.path)
    }

    @Test
    fun testCurrentVersionDatabase() = runBlocking {
        Assert.assertEquals(1, db.openHelper.readableDatabase.version)
    }

    @Test
    fun databaseIsOpen() = runBlocking {
        Assert.assertEquals(true, db.isOpen)
    }

    @Test
    fun databaseIsClosed() = runBlocking {
        Assert.assertEquals(false, db.isOpen)
    }



    @After
    fun closeDb(){
        db.close()
    }
}