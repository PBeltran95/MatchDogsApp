package ar.com.example.matchdogs.presentation.favorites

import androidx.lifecycle.*
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.domain.local.LocalDogRepo
import ar.com.example.matchdogs.domain.remote.RepositoryOfDogs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class FavoriteDogViewModel @Inject constructor(private val localRepo: LocalDogRepo) : ViewModel() {

    fun fetchFavoriteDogs() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {

        try {
            emit((localRepo.getFavoriteDogs()))
        }catch (e:java.lang.Exception){

        }
    }


    fun saveFavoriteDog(dog: DogEntity) {
        viewModelScope.launch {
            localRepo.saveDog(dog)
        }
    }

    fun deleteDog(dog: DogEntity){
        viewModelScope.launch {
            localRepo.deleteDog(dog)
        }
    }


}

