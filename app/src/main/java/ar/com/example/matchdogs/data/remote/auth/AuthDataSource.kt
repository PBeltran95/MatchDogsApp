package ar.com.example.matchdogs.data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import ar.com.example.matchdogs.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class AuthDataSource @Inject constructor (val authInstance: FirebaseAuth, val firebaseFireStore: FirebaseFirestore) {

    suspend fun signIn(email:String, password:String):FirebaseUser?{
        val authResult = authInstance.signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, username: String, userImage: Bitmap?): FirebaseUser? {
        val authResult = authInstance.createUserWithEmailAndPassword(email, password).await()

        val user = authInstance.currentUser
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        val baos = ByteArrayOutputStream()
        userImage?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        authResult.user?.uid?.let {
            firebaseFireStore.collection("users").document(it).set(User(email, username, downloadUrl) )
        }

        return authResult.user
    }

}