package ar.com.example.matchdogs.presentation.adoptScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ar.com.example.matchdogs.core.Response
import ar.com.example.matchdogs.domain.remote.RepositoryOfDogs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor (private val repo:RepositoryOfDogs): ViewModel() {

    fun fetchDogs() = liveData(viewModelScope.coroutineContext + Dispatchers.IO){
        emit(Response.Loading())
        try {
            emit(Response.Success(repo.getDogs()))
        }catch (e:Exception){
            emit(Response.Failure(e))
        }
    }

}
