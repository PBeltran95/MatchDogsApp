package ar.com.example.matchdogs.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.core.show
import ar.com.example.matchdogs.core.toast
import ar.com.example.matchdogs.databinding.FragmentLoginBinding
import ar.com.example.matchdogs.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        toRegister()
    }

    private fun toRegister() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun doLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }

    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

    }

    private fun validateCredentials(email:String, password:String){
        val REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        when{
            email.isEmpty() -> binding.etEmail.error = "Email is empty"
            !REGEX.toRegex().matches(email) -> binding.etEmail.error = "Invalid Email"
            !email.contains("@") -> binding.etEmail.error = "Invalid Email"
        }
        if (password.isEmpty()){
            binding.etPassword.error = "Password is empty"
        }
    }
    private fun signIn(email:String, password:String){
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer {
            when(it){
                is Response.Loading -> {
                    binding.progressBar.show()
                    binding.btnLogin.isEnabled = false
                }
                is Response.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Response.Failure -> {
                    binding.progressBar.hide()
                    binding.btnLogin.isEnabled = true
                    toast(requireContext(),"Error: ${it.throwable}")
                }
            }
        })
    }

}