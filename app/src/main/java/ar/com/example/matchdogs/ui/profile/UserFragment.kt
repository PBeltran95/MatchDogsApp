package ar.com.example.matchdogs.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.core.show
import ar.com.example.matchdogs.core.toast
import ar.com.example.matchdogs.databinding.FragmentUserBinding
import ar.com.example.matchdogs.presentation.auth.AuthViewModel
import ar.com.example.matchdogs.presentation.nightMode.ScreenModeViewModel
import com.bumptech.glide.Glide
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

                        binding.imgUser.hide()
                        findNavController().navigate(R.id.action_userFragment_to_loginFragment)
                    }
                    is Response.Failure -> {
                        toast(requireContext(), "An error happened: ${it.throwable}")
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
                    binding.progressBar.hide()
                    val circleProgressBar = CircularProgressDrawable(requireContext()).apply {
                        strokeWidth = 5f
                        centerRadius = 30f
                        start()
                    }
                    Glide.with(requireContext()).load(user?.photoUrl).placeholder(circleProgressBar).into(binding.imgUser)
                    binding.tvUserName.text = user?.username

                }
                is Response.Failure -> { toast(requireContext(), "Cant recover a valid user") }
            }
        })
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