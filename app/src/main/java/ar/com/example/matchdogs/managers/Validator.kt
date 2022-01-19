package ar.com.example.matchdogs.managers

interface Validator {

    fun validateNameAndGame(dogName:String): Boolean

    abstract fun validateGame(dogGame: String): Boolean

}