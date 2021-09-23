package ar.com.example.matchdogs.domain.auth

import android.graphics.Bitmap
import ar.com.example.matchdogs.data.models.User
import com.google.firebase.auth.FirebaseUser

interface LoginRepo {

    suspend fun signIn(email:String, password:String):FirebaseUser?
    suspend fun signUp(email: String, password: String, username:String, userImage: Bitmap?): FirebaseUser?
    suspend fun getUserInfo():User?
    suspend fun logOut(): Unit

}