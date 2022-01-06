package ar.com.example.matchdogs.ui.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.*
import ar.com.example.matchdogs.data.models.User
import ar.com.example.matchdogs.databinding.FragmentUserBinding
import ar.com.example.matchdogs.presentation.auth.AuthViewModel
import ar.com.example.matchdogs.presentation.nightMode.ScreenModeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding : FragmentUserBinding
    private val authViewModel by viewModels<AuthViewModel>()
    private val screenModeViewModel by viewModels<ScreenModeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        switchMode()
        rememberScreenMode()
        recoverUserPhoto()
        signOut()
    }

    private fun signOut() {
        binding.btnSignOut.setOnClickListener {
            authViewModel.logOut().observe(viewLifecycleOwner, Observer {
                when(it){
                    is Response.Loading -> {
                        binding.progressBar.show()
                    }
                    is Response.Success -> {
                        findNavController().navigate(R.id.action_userFragment_to_loginFragment)
                        binding.imgUser.hide()

                    }
                    is Response.Failure -> {
                        toast(requireContext(), getString(R.string.sign_out_error, it.throwable))
                    }
                }
            })
        }

    }

    private fun recoverUserPhoto() {
        authViewModel.getUserInfo().observe(viewLifecycleOwner, Observer {
            when(it){
                is Response.Loading -> {
                    binding.progressBar.show()
                }
                is Response.Success -> {
                    val user = it.data
                    drawUserData(user)
                }
                is Response.Failure -> { toast(requireContext(), getString(R.string.user_img_error)) }
            }
        })
    }

    private fun drawUserData(user: User?) {
        binding.imgUser.show()
        binding.progressBar.hide()
        setGlide(requireContext(), user?.photoUrl, binding.imgUser)
        binding.tvUserName.text = user?.username
    }

    private fun rememberScreenMode() {
        screenModeViewModel.fetchScreenMode().observe(viewLifecycleOwner, Observer {
            if (it == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.modeSwitch.isChecked = true

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
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
        screenModeViewModel.saveScreenMode(mode)
    }
    private fun darkMode() {
        val mode = true
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        screenModeViewModel.saveScreenMode(mode)
    }

}