package ar.com.example.matchdogs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat

import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.data.preferences.PreferencesProvider
import ar.com.example.matchdogs.databinding.ActivityMainBinding
import ar.com.example.matchdogs.presentation.adoptScreen.DogViewModel
import ar.com.example.matchdogs.presentation.nightMode.ScreenModeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val screenModeViewModel by viewModels<ScreenModeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bNavigationView.setupWithNavController(navController)
        rememberScreenMode()
        hideBottomBar(navController)

    }

    private fun hideBottomBar(navController:NavController) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.contractFragment -> {binding.bNavigationView.hide()}

                R.id.loginFragment -> {binding.bNavigationView.hide()}

                R.id.registerFragment -> {binding.bNavigationView.hide()}

                else -> {
                    binding.bNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun rememberScreenMode() {
        screenModeViewModel.fetchScreenMode().observe(this, Observer {
            if (it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
        })
    }
}