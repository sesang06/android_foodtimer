package com.sesang06.foodtimer.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModelFactory (
    private val mainTimerUseCase: MainTimerUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(mainTimerUseCase) as T
    }
}

class MainViewModel(
    private val mainTimerUseCase: MainTimerUseCase
) : ViewModel()  {

    val mainItems: MutableLiveData<List<MainItem>> by lazy {
        MutableLiveData<List<MainItem>>()
    }


    fun onCreate() {
        viewModelScope.launch(Dispatchers.IO) {
            mainTimerUseCase.loadDefaultDataIfNeeded()

            val items = mainTimerUseCase.fetchData()
            mainItems.postValue(items)
        }
    }

}