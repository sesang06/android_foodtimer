package com.sesang06.foodtimer

import android.app.usage.UsageEvents
import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity
import com.sesang06.foodtimer.database.TimerImage
import com.sesang06.foodtimer.timer.RunningTimerPresenter
import com.sesang06.foodtimer.timer.TimerPresenter
import com.sesang06.foodtimer.timer.TimerUseCase
import com.sesang06.foodtimer.timer.TimerViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TimerViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var timerViewModel: TimerViewModel



    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val timerDataSource = TimerDataSource(appContext)
    private companion object {
        const val title = "EGG"
        const val description = "3분"
        const val minutes = 40
        const val seconds = 10
        val thumbnail = TimerImage.DISH
    }


    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }


    @After
    fun after() {
        Dispatchers.resetMain()
        // Reset Coroutine Dispatcher and Scope.
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    private fun createTimerEntity() = TimerEntity(
        0,
        title, description, minutes, seconds, thumbnail
    )


    @Test
    fun timerTest() {
        runBlockingTest {

            timerDataSource.deleteAll()
            val created = timerDataSource.insertTimer(createTimerEntity())
            val timerUseCase = TimerUseCase(created.id, timerDataSource)
            timerViewModel = TimerViewModel(testScope, created.id, timerUseCase)
            timerViewModel.onCreate()

            val t = timerViewModel.timer.getOrAwaitValue()
            assert(t != null)


            timerViewModel.startTimer()

            // Then the new task event is triggered
            val value = timerViewModel.runningTimer.getOrAwaitValue(5)

            assert(value != null)
            value?.let {

                assertEquals(it.seconds, 10)
            }
            runBlocking<Unit> {

                delay(1000)

            }
            // Then the new task event is triggered
            val nextValue = timerViewModel.runningTimer.getOrAwaitValue(5)
            assert(nextValue != null)
            nextValue?.let {

                assertEquals(it.seconds, 9)
            }
        }

    }

    @Test
    fun stopTimerTest() {
        runBlockingTest {

            timerDataSource.deleteAll()
            val created = timerDataSource.insertTimer(createTimerEntity())
            val timerUseCase = TimerUseCase(created.id, timerDataSource)
            timerViewModel = TimerViewModel(testScope, created.id, timerUseCase)
            timerViewModel.onCreate()

            val t = timerViewModel.timer.getOrAwaitValue()
            assert(t != null)


            timerViewModel.startTimer()

            // Then the new task event is triggered
            val value = timerViewModel.runningTimer.getOrAwaitValue(5)

            assert(value != null)
            value?.let {

                assertEquals(it.seconds, 10)
            }
            runBlocking<Unit> {

                delay(1000)

            }
            // Then the new task event is triggered
            val nextValue = timerViewModel.runningTimer.getOrAwaitValue(5)
            assert(nextValue != null)
            nextValue?.let {
                assertEquals(it.seconds, 9)
            }

            timerViewModel.stopTimer()
            val state = timerViewModel.state.getOrAwaitValue()
            assert(state != null)
            assert(state == TimerViewModel.State.stop)
            runBlocking<Unit> {
                delay(1000)
            }
            val stoppedValue = timerViewModel.runningTimer.getOrAwaitValue(5)
            assert(stoppedValue != null)
            stoppedValue?.let {
                assertEquals(it.seconds, 9)
            }

            timerViewModel.restartTimer()
            val stoppedState = timerViewModel.state.getOrAwaitValue()
            assert(stoppedState != null)
            assert(stoppedState == TimerViewModel.State.running)
            runBlocking<Unit> {
                delay(1000)
            }
            val restartedValue = timerViewModel.runningTimer.getOrAwaitValue(5)
            assert(restartedValue != null)

            restartedValue?.let {
                assertEquals(it.seconds, 8)
            }


        }
    }

    @Test
    fun processTest() {
        timerDataSource.deleteAll()
        val created = timerDataSource.insertTimer(createTimerEntity())
        val timerUseCase = TimerUseCase(created.id, timerDataSource)
        timerViewModel = TimerViewModel(testScope, created.id, timerUseCase)
        timerViewModel.onCreate()


        val total = TimerPresenter("","", 10, 10)
        val current = RunningTimerPresenter(10, 10)
        val process =  timerViewModel.process(total, current)
        assert(process == 0)

    }


    @Test
    fun processTestTwo() {
        timerDataSource.deleteAll()
        val created = timerDataSource.insertTimer(createTimerEntity())
        val timerUseCase = TimerUseCase(created.id, timerDataSource)
        timerViewModel = TimerViewModel(testScope, created.id, timerUseCase)
        timerViewModel.onCreate()


        val total = TimerPresenter("","", 10, 10)
        val current = RunningTimerPresenter(0, 0)
        val process =  timerViewModel.process(total, current)
        assert(process == 100)

    }
}


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}