package ar.com.example.matchdogs.presentation.favorites

import androidx.lifecycle.*
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.domain.local.LocalDogRepo
import ar.com.example.matchdogs.domain.remote.RepositoryOfDogs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.Exception

class FavoriteDogViewModel(private val localRepo: LocalDogRepo) : ViewModel() {

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


}

class FavoriteDogViewModelFactory(private val repo: LocalDogRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LocalDogRepo::class.java).newInstance(repo)
    }

}

