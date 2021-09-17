package ar.com.example.matchdogs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SwitchCompat
import android.view.Menu

import android.view.MenuInflater
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.data.preferences.PreferencesProvider




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ar.com.example.matchdogs.R.layout.activity_main)

    }



}