package com.sesang06.foodtimer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sesang06.foodtimer.database.TimerUseCase
import com.sesang06.foodtimer.main.MainItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModelFactory (
    private val timerUseCase: TimerUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(timerUseCase) as T
    }
}

class MainViewModel(
    private val timerUseCase: TimerUseCase
) : ViewModel()  {

    val mainItems: MutableLiveData<List<MainItem>> by lazy {
        MutableLiveData<List<MainItem>>()
    }


    fun onCreate() {
        viewModelScope.launch(Dispatchers.IO) {
            timerUseCase.loadDefaultDataIfNeeded()

            val items = timerUseCase.fetchData()
            mainItems.postValue(items)
        }
    }

}