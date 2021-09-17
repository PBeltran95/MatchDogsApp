package ar.com.example.matchdogs.domain.local.sharedPreferences

interface SharedRepo {


    suspend fun saveScreenMode(screenMode:Boolean)

    suspend fun getScreenMode():Boolean

}