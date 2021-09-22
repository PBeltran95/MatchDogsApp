package ar.com.example.matchdogs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat

import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.data.preferences.PreferencesProvider
import ar.com.example.matchdogs.databinding.ActivityMainBinding
import ar.com.example.matchdogs.presentation.adoptScreen.DogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController
        //supportActionBar?.hide()
        binding.bNavigationView.setupWithNavController(navController)


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


}

/*<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bNavigationView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:menu="@menu/nav_menu"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>*/