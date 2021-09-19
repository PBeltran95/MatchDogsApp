package ar.com.example.matchdogs.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_MODE = "MyDataBase"


class PreferencesProvider(
    context: Context
) {
    private val appContext = context.applicationContext

    private val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)


    fun saveScreenMode(mode:Boolean){
        preferences.edit().putBoolean(
            KEY_MODE,
            mode
        ).apply()

    }

    fun getScreenMode():Boolean{
        return preferences.getBoolean(KEY_MODE, false)
    }
}