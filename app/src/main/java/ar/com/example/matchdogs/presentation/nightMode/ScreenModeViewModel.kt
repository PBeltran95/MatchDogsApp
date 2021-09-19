package ar.com.example.matchdogs.presentation.nightMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ar.com.example.matchdogs.domain.local.LocalDogRepo
import ar.com.example.matchdogs.domain.local.LocalDogRepoImpl
import ar.com.example.matchdogs.domain.local.sharedPreferences.SharedRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class ScreenModeViewModel(private val sharedRepo: SharedRepo) : ViewModel() {


    fun saveScreenMode(screenMode:Boolean){
        viewModelScope.launch {
            sharedRepo.saveScreenMode(screenMode)
        }
    }


    fun fetchScreenMode() = liveData(viewModelScope.coroutineContext + Dispatchers.IO){
        emit(sharedRepo.getScreenMode())
    }
}


class ScreenModeViewModelFactory(private val repo: SharedRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SharedRepo::class.java).newInstance(repo)
    }
}