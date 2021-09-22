package ar.com.example.matchdogs.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.databinding.FragmentHomeBinding
import ar.com.example.matchdogs.presentation.nightMode.ScreenModeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel by viewModels<ScreenModeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        switchMode()
        rememberScreenMode()
        onBackPressed()
        recoverUserPhoto()
    }

    private fun recoverUserPhoto() {

    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })
    }




    private fun rememberScreenMode() {
        viewModel.fetchScreenMode().observe(viewLifecycleOwner, Observer {
            if (it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.modeSwitch.isChecked = true

            }else{AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
        })
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