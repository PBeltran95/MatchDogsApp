package ar.com.example.matchdogs.presentation.auth

import android.graphics.Bitmap
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.domain.auth.LoginRepo
import ar.com.example.matchdogs.managers.NewUserValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: LoginRepo,
    private val newUserValidator: NewUserValidator
) : ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    fun signUp(email: String, password: String, username: String, userImage: Bitmap?) =
        liveData(Dispatchers.IO) {
            emit(Response.Loading())
            try {
                emit(Response.Success(repo.signUp(email, password, username, userImage)))
            } catch (e: Exception) {
                emit(Response.Failure(e))
            }
        }

    fun getUserInfo() = liveData(Dispatchers.IO) {
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getUserInfo()))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    fun logOut() = liveData(Dispatchers.IO) {
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.logOut()))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean>
        get() = _isButtonEnabled

    fun validateFields(email: String, password: String) {

        val fieldsEmpty: Boolean =
            email.isEmpty() || password.isEmpty()
        val emailFormat: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()


        _isButtonEnabled.value = !fieldsEmpty && emailFormat
    }


    private val _validUser = MutableLiveData(false)
    val validUser: LiveData<Boolean>
        get() = _validUser

    fun validateNewUserValues(image: Bitmap?, username: String, email: String, password: String, confirmPassword: String){
        _validUser.value =
            (with(newUserValidator){
                isNotNullImage(image)
                checkUserName(username)
                checkEmail(email)
                checkPassword(password)
                checkEmptyConfirmPassword(confirmPassword)
            })
    }


    private val _passwordMatches = MutableLiveData(false)
    val passwordMatches: LiveData<Boolean>
        get() = _passwordMatches

    fun checkPasswordMatches(password: String, confirmPassword: String){
        _passwordMatches.value = newUserValidator.checkPasswordMatches(password, confirmPassword)
    }



}