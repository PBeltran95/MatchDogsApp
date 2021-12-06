package ar.com.example.matchdogs.ui.auth.login

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.core.show
import ar.com.example.matchdogs.core.toast
import ar.com.example.matchdogs.databinding.FragmentLoginBinding
import ar.com.example.matchdogs.presentation.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        listenComponents()
        setupObservers()
        setupButton()
        navigateToSignUp()
        isUserLogged()
    }

    private fun isUserLogged() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_adoptScreenFragment)
        }
    }

    private fun navigateToSignUp() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun listenComponents() {
        binding.etEmail.doAfterTextChanged { takeAndSendValues() }
        binding.etPassword.doAfterTextChanged { takeAndSendValues() }
    }



    private fun takeAndSendValues() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        viewModel.validateFields(email, password)
    }


    private fun setupObservers() {
        viewModel.isButtonEnabled.observe(viewLifecycleOwner,{
            enableOrDisableButton(it)
        })
    }

    private fun enableOrDisableButton(isButtonEnabled: Boolean) {
        if (!isButtonEnabled){
            binding.btnLogin.isEnabled = false
            binding.btnLogin.setBackgroundColor(Color.LTGRAY)
        }else{
            binding.btnLogin.isEnabled = true
            binding.btnLogin.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.orange))
        }
    }

    private fun setupButton(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            observeLoginValue(email, password)
        }
    }

    private fun observeLoginValue(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, {
            when(it){
                is Response.Loading ->{
                    showProgressBar()
                }
                is Response.Success ->{
                    loginAndNavigate()
                }
                is Response.Failure ->{
                    showError(it)
                }
            }
        })
    }

    private fun showError(response: Response.Failure<FirebaseUser?>) {
        binding.progressBar.hide()
        toast(requireContext(), response.throwable.message!!)
        binding.btnLogin.isEnabled = true
    }

    private fun loginAndNavigate() {
        binding.progressBar.hide()
        findNavController().navigate(R.id.action_loginFragment_to_adoptScreenFragment)
    }

    private fun showProgressBar() {
        binding.progressBar.show()
        binding.btnLogin.isEnabled = false
    }

}