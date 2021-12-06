package ar.com.example.matchdogs.ui.auth.signUp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ar.com.example.matchdogs.R
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.core.hide
import ar.com.example.matchdogs.core.show
import ar.com.example.matchdogs.core.toast
import ar.com.example.matchdogs.databinding.FragmentRegisterBinding
import ar.com.example.matchdogs.presentation.auth.AuthViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding:FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()
    private var bitmap: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
        takeUserImage()
    }

    private fun takeUserImage() {
        binding.imgUser.setOnClickListener {
            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                openActivityForResult(takePicture)
            }catch (e: ActivityNotFoundException){
                toast(requireContext(), getString(R.string.camera_error))
            }
        }
    }
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.imgUser.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }

    private fun openActivityForResult(takePicture: Intent) {
        startForResult.launch(takePicture)
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val userImage = bitmap
            val userName = binding.editTextUserName.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()

            if (validateUserData(password, confirmPassword, userName, email, userImage)) return@setOnClickListener

            createUser(email,password,userName, userImage)
        }

    }

    private fun createUser(email: String, password: String, userName: String, userImage:Bitmap?) {
        viewModel.signUp(email,password,userName, userImage).observe(viewLifecycleOwner, Observer {
            when(it){
                is Response.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignUp.isEnabled = false
                }
                is Response.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_registerFragment_to_adoptScreenFragment)
                }
                is Response.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignUp.isEnabled = true
                    toast(requireContext(), getString(R.string.error_of_calling_server, it.throwable))
                }
            }
        })
    }

    private fun validateUserData(
        password: String,
        confirmPassword: String,
        userName: String,
        email: String,
        image: Bitmap?
    ) : Boolean {
        if (password != confirmPassword) {
            binding.textInputLayoutPass.endIconMode = TextInputLayout.END_ICON_NONE
            binding.textInputLayoutCofirm.endIconMode = TextInputLayout.END_ICON_NONE
            binding.editTextPassword.error = getString(R.string.pass_no_match)
            binding.editTextConfirmPassword.error = getString(R.string.pass_no_match)
            return true
        } else {
            binding.textInputLayoutPass.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            binding.textInputLayoutCofirm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }

        if (image == null){
            return true
        }


        if (userName.isEmpty()) {
            binding.editTextUserName.error = getString(R.string.user_empty)
            return true
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = getString(R.string.empty_email)
            return true
        }

        if (password.isEmpty()) {
            binding.textInputLayoutPass.endIconMode = TextInputLayout.END_ICON_NONE
            binding.editTextPassword.error = getString(R.string.empty_pass)
            return true
        } else binding.textInputLayoutPass.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        //isEndIconVisible

        if (confirmPassword.isEmpty()) {
            binding.textInputLayoutCofirm.endIconMode = TextInputLayout.END_ICON_NONE
            binding.editTextConfirmPassword.error = getString(R.string.pass_confirmation_empty)
            return true
        } else binding.textInputLayoutCofirm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        return false
    }


}