package ar.com.example.matchdogs.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.data.models.DogEntity
import ar.com.example.matchdogs.domain.local.LocalDogRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDogViewModel @Inject constructor(private val localRepo: LocalDogRepo) : ViewModel() {

    fun fetchFavoriteDogs() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Response.Loading<Nothing>())
        try {
            emit(Response.Success(localRepo.getFavoriteDogs()))
        }catch (e:java.lang.Exception){
            emit(Response.Failure(e))
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

