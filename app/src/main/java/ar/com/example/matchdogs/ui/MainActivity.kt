package ar.com.example.matchdogs.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.databinding.ActivityMainBinding
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
        navController.addOnDestinationChangedListener { _, destination, _ ->
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
        screenModeViewModel.fetchScreenMode().observe(this) {
            if (it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
        }
    }
}