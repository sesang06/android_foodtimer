package com.sesang06.foodtimer.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sesang06.foodtimer.main.MainTimerUseCase
import com.sesang06.foodtimer.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.util.NotificationLite.disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

class TimerViewModelFactory (
    private val id: Int, private val useCase: TimerUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TimerViewModel(null, id, useCase) as T
    }
}

class TimerViewModel(
    private val coroutineScopeProvider: CoroutineScope? = null,
    private val id: Int, private val useCase: TimerUseCase
) : ViewModel() {

    enum class State {
        beforeStart, running, done, stop
    }


    private val coroutineScope = getViewModelScope(coroutineScopeProvider)


    val timer: MutableLiveData<TimerPresenter> by lazy {
        MutableLiveData<TimerPresenter>()
    }


    val runningTimer: MutableLiveData<RunningTimerPresenter> by lazy {
        MutableLiveData<RunningTimerPresenter>()
    }

    val progress: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val state: MutableLiveData<State> by lazy {
        MutableLiveData<State>()
    }




    private fun totalSeconds(minutes: Int, seconds: Int): Int {
        return minutes * 60 + seconds
    }

    fun process(total: TimerPresenter, current: RunningTimerPresenter): Int {
        val totalSeconds = totalSeconds(total.minutes, total.seconds)
        val currentSeconds = totalSeconds(current.minutes, current.seconds)
        return 100 - currentSeconds * 100 / totalSeconds
    }

    private val timerDisposable = CompositeDisposable()

    override fun onCleared() {
        timerDisposable.dispose()
        super.onCleared()
    }

    fun onCreate() {
        state.postValue(State.beforeStart)
        coroutineScope.launch(Dispatchers.IO) {
            timer.postValue(useCase.fetch())

        }
    }

    fun editTimer(minutes: Int, seconds: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            timer.postValue(useCase.editTime(minutes, seconds))
        }
    }


    private fun dispatchTimer(totalSeconds: Int) {
        timerDisposable.clear()
        timerDisposable.add(Observable
            .interval(0, 1, TimeUnit.SECONDS)
            .subscribe {
                val currentLeftTime = totalSeconds - it.toInt()

                val presenter = RunningTimerPresenter(
                    currentLeftTime / 60,
                    currentLeftTime % 60
                )

                if (currentLeftTime == 0) {
                    state.postValue(State.done)
                    timerDisposable.clear()
                }
                timer.value?.let {
                    val progressValue = process(it, presenter)
                    progress.postValue(progressValue)
                }
                runningTimer.postValue(presenter)

            }
        )
    }

    fun startTimer() {
        val minutes = timer.value?.minutes ?: return
        val seconds = timer.value?.seconds ?: return


        val totalSeconds = totalSeconds(minutes, seconds)
        state.postValue(State.running)
        dispatchTimer(totalSeconds)
    }

    fun stopTimer() {
        timerDisposable.clear()
        state.postValue(State.stop)
    }

    fun restartTimer() {
        val minutes = runningTimer.value?.minutes ?: return
        val seconds = runningTimer.value?.seconds ?: return

        val totalSeconds = totalSeconds(minutes, seconds)
        state.postValue(State.running)
        dispatchTimer(totalSeconds)
    }



}