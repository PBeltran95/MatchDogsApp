package ar.com.example.matchdogs.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import ar.com.example.matchdogs.application.AppConstants
import javax.inject.Inject


class PreferencesProvider @Inject constructor (
    private val preferences : SharedPreferences
) {

    fun saveScreenMode(mode:Boolean){
        preferences.edit().putBoolean(
            "NIGHT_MODE",
            mode
        ).apply()

    }

    fun getScreenMode():Boolean{
        return preferences.getBoolean("NIGHT_MODE", false)
    }
}