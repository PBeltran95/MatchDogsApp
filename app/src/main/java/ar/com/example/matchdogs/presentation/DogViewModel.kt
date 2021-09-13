package ar.com.example.matchdogs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.domain.remote.RepositoryOfDogs
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DogViewModel(private val repo:RepositoryOfDogs): ViewModel() {

    fun fetchDogs(breed:String) = liveData(viewModelScope.coroutineContext + Dispatchers.IO){
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getDogs(breed)))
        }catch (e:Exception){
            emit(Response.Failure(e))
        }
    }

}

class DogViewModelFactory(private val repo: RepositoryOfDogs):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepositoryOfDogs::class.java).newInstance(repo)
    }

}