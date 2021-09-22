package ar.com.example.matchdogs.domain.auth

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser

interface LoginRepo {

    suspend fun signIn(email:String, password:String):FirebaseUser?
    suspend fun signUp(email: String, password: String, username:String, userImage: Bitmap?): FirebaseUser?

}