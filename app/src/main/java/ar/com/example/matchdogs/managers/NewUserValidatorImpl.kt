package ar.com.example.matchdogs.managers

import android.graphics.Bitmap
import android.util.Patterns

class NewUserValidatorImpl: NewUserValidator {

    override fun isNotNullImage(image: Bitmap?): Boolean {
        return image != null
    }

    override fun checkUserName(userName: String): Boolean {
        return userName.isNotEmpty()
    }

    override fun checkEmail(email: String): Boolean {
        return (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    override fun checkPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    override fun checkEmptyConfirmPassword(confirmPassword: String): Boolean {
        return confirmPassword.isNotEmpty()
    }

    override fun checkPasswordMatches(password: String, confirmPassword: String): Boolean {
        return (password == confirmPassword)
    }
}