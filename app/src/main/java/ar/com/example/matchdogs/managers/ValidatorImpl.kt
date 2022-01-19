package ar.com.example.matchdogs.managers

class ValidatorImpl: Validator {

    override fun validateNameAndGame(dogName: String): Boolean {
        return dogName.isNotEmpty()
    }

    override fun validateGame(dogGame: String): Boolean {
        return dogGame.isNotEmpty()
    }
}