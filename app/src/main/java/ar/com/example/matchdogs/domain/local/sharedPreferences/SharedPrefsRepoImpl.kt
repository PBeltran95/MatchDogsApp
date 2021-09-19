package ar.com.example.matchdogs.domain.local.sharedPreferences

import ar.com.example.matchdogs.data.preferences.PreferencesProvider


class SharedPrefsRepoImpl(private val prefs: PreferencesProvider): SharedRepo {


    override suspend fun saveScreenMode(screenMode:Boolean){
        prefs.saveScreenMode(screenMode)
    }

    override suspend fun getScreenMode() = prefs.getScreenMode()
}