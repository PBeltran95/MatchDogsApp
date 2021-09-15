package ar.com.example.matchdogs.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.domain.local.LocalDogRepo
import ar.com.example.matchdogs.domain.remote.RepositoryOfDogs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoriteDogViewModel(private val localRepo: LocalDogRepo) : ViewModel() {

    fun fetchFavoriteDogs() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit((localRepo.getFavoriteDogs()))
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

