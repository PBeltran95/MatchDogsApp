package ar.com.example.matchdogs.domain.auth

import android.graphics.Bitmap
import ar.com.example.matchdogs.data.models.User
import ar.com.example.matchdogs.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginRepoImpl @Inject constructor (private val dataSource: AuthDataSource) : LoginRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)

    override suspend fun signUp(email: String, password: String, username:String, userImage: Bitmap?): FirebaseUser? =
        dataSource.signUp(email,password, username, userImage)

    override suspend fun getUserInfo(): User? = dataSource.getUserInfo()


    override suspend fun logOut() = dataSource.logOut()


}