package ar.com.example.matchdogs.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.data.local.AppDataBase
import ar.com.example.matchdogs.data.local.LocalDogDataSource
import ar.com.example.matchdogs.data.preferences.PreferencesProvider
import ar.com.example.matchdogs.databinding.FragmentHomeBinding
import ar.com.example.matchdogs.domain.local.LocalDogRepoImpl
import ar.com.example.matchdogs.domain.local.sharedPreferences.SharedPrefsRepoImpl
import ar.com.example.matchdogs.presentation.favorites.FavoriteDogViewModel
import ar.com.example.matchdogs.presentation.favorites.FavoriteDogViewModelFactory
import ar.com.example.matchdogs.presentation.nightMode.ScreenModeViewModel
import ar.com.example.matchdogs.presentation.nightMode.ScreenModeViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel by viewModels<ScreenModeViewModel> { ScreenModeViewModelFactory(SharedPrefsRepoImpl(
        PreferencesProvider(requireContext())
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        toAnotherScreen()
        switchMode()
        rememberScreenMode()
    }

    private fun rememberScreenMode() {
        viewModel.fetchScreenMode().observe(viewLifecycleOwner, Observer {
            if (it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.modeSwitch.isChecked = true

            }else{AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
        })
    }

    private fun toAnotherScreen() {
        binding.btnAdopt.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_adoptScreenFragment)
        }
        binding.btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun switchMode(){
        binding.modeSwitch.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked){
                darkMode()
            }else{ lightMode() }
        }
    }

    private fun lightMode() {
        val mode = false
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        viewModel.saveScreenMode(mode)
    }
    private fun darkMode() {
        val mode = true
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        viewModel.saveScreenMode(mode)
    }

}