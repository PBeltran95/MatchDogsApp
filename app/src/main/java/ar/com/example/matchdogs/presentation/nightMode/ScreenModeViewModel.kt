package ar.com.example.matchdogs.presentation.nightMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ar.com.example.matchdogs.domain.local.sharedPreferences.SharedRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScreenModeViewModel @Inject constructor (private val sharedRepo: SharedRepo) : ViewModel() {


    fun saveScreenMode(screenMode:Boolean){
        viewModelScope.launch {
            sharedRepo.saveScreenMode(screenMode)
        }
    }


    fun fetchScreenMode() = liveData(viewModelScope.coroutineContext + Dispatchers.IO){
        emit(sharedRepo.getScreenMode())
    }
}

