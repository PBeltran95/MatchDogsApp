package ar.com.example.matchdogs.managers

import android.graphics.Bitmap

interface NewUserValidator {

    fun isNotNullImage(image: Bitmap?): Boolean

    fun checkUserName(userName:String): Boolean

    fun checkEmail(email: String): Boolean

    fun checkPassword(password: String): Boolean

    fun checkEmptyConfirmPassword(confirmPassword: String): Boolean

    fun checkPasswordMatches(password: String, confirmPassword: String): Boolean

}