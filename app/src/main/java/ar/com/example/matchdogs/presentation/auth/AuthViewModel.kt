package ar.com.example.matchdogs.presentation.auth

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.domain.auth.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val repo: LoginRepo):ViewModel() {

    fun signIn(email:String, password:String) = liveData(Dispatchers.IO){
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.signIn(email, password)))
        }catch (e:Exception){
            emit(Response.Failure(e))
        }
    }
    fun signUp(email:String, password:String, username:String, userImage:Bitmap?) = liveData(Dispatchers.IO){
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.signUp(email, password, username, userImage)))
        }catch (e:Exception){
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

    fun logOut()  = liveData(Dispatchers.IO){
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.logOut()))
        }catch (e:Exception){
            emit(Response.Failure(e))
        }
    }


}