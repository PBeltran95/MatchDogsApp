package ar.com.example.matchdogs.presentation.favorites

import androidx.lifecycle.*
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.domain.local.LocalDogRepo
import ar.com.example.matchdogs.managers.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDogViewModel @Inject constructor(private val localRepo: LocalDogRepo, private val validator: Validator) : ViewModel() {

    fun fetchFavoriteDogs() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Response.Loading<Nothing>())
        try {
            emit(Response.Success(localRepo.getFavoriteDogs()))
            checkEmptyList(localRepo.getFavoriteDogs())
        }catch (e:java.lang.Exception){
            emit(Response.Failure(e))
        }
    }

    private val _isTheListOfDogsEmpty = MutableLiveData(false)
    val isTheListOfDogsEmpty: LiveData<Boolean>
        get() = _isTheListOfDogsEmpty

    private fun checkEmptyList(favoriteDogs: List<DogEntity>) {
        viewModelScope.launch(Dispatchers.Main) {
            _isTheListOfDogsEmpty.value = (favoriteDogs.isEmpty())
        }
    }


    fun saveFavoriteDog(dog: DogEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepo.saveDog(dog)
        }
    }

    fun deleteDog(dog: DogEntity){
        viewModelScope.launch {
            localRepo.deleteDog(dog)
        }
    }

    private val _isDogDataValid = MutableLiveData(false)
    val isDogDataValid: LiveData<Boolean>
        get() = _isDogDataValid

    fun validateDogData(){
        _isDogDataValid.value = (_isNameValid.value == true && _isGameValid.value == true)
    }


    private val _isNameValid = MutableLiveData<Boolean>()

    fun validateDogName(dogName: String) {
        _isNameValid.value = validator.validateNameAndGame(dogName)
    }

    private val _isGameValid = MutableLiveData<Boolean>()

    fun validateDogGame(dogGame: String) {
        _isGameValid.value = validator.validateGame(dogGame)
    }



}

