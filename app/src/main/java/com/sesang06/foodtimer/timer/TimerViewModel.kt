package com.sesang06.foodtimer.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesang06.foodtimer.main.MainItem
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
/**
 * Configure CoroutineScope injection for production and testing.
 *
 * @receiver ViewModel provides viewModelScope for production
 * @param coroutineScope null for production, injects TestCoroutineScope for unit tests
 * @return CoroutineScope to launch coroutines on
 */
fun ViewModel.getViewModelScope(coroutineScope: CoroutineScope?) =
    if (coroutineScope == null) this.viewModelScope
    else coroutineScope

class TimerViewModel(   private val coroutineScopeProvider: CoroutineScope? = null,
                        private val id: Int, private val useCase: TimerUseCase): ViewModel() {

    enum class State {
        beforeStart, running, done
    }


    private val coroutineScope = getViewModelScope(coroutineScopeProvider)


    val timer: MutableLiveData<TimerPresenter> by lazy {
        MutableLiveData<TimerPresenter>()
    }


    val runningTimer: MutableLiveData<RunningTimerPresenter> by lazy {
        MutableLiveData<RunningTimerPresenter>()
    }

    private val timerDisposeable = CompositeDisposable()


    fun onCreate() {
        coroutineScope.launch {
            timer.postValue(useCase.fetch())

        }
    }

    fun editTimer(minutes: Int, seconds: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            timer.postValue(useCase.editTime(minutes, seconds))
        }
    }



    fun startTimer() {
        val minutes = timer.value?.minutes ?: return
        val seconds = timer.value?.seconds ?: return


        val totalSeconds = minutes * 60 + seconds

        timerDisposeable.clear()
        timerDisposeable.add(Observable
                .interval(0,1, TimeUnit.SECONDS)
                .subscribe{


                    val currentLeftTime = totalSeconds - it.toInt()

                    val presenter = RunningTimerPresenter(
                            currentLeftTime / 60,
                            currentLeftTime % 60
                    )

                    if (currentLeftTime == 0) {
                        timerDisposeable.clear()
                    }
                    runningTimer.postValue(presenter)

                }
        )
    }

}