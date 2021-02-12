package com.sesang06.foodtimer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sesang06.foodtimer.database.AppInstalledRepository
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity
import com.sesang06.foodtimer.database.TimerImage
import com.sesang06.foodtimer.main.MainTimerUseCase
import com.sesang06.foodtimer.timer.TimerUseCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerUseCaseTest {


    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val timerDataSource = TimerDataSource(appContext)


    private companion object {
        const val title = "EGG"
        const val description = "3분"
        const val minutes = 40
        const val seconds = 10
        val thumbnail = TimerImage.DISH
    }

    private fun createTimerEntity() = TimerEntity(
            0,
            title, description, minutes, seconds, thumbnail
    )
    @Test
    fun fetchDataTest() {
        timerDataSource.deleteAll()
        val created = timerDataSource.insertTimer(createTimerEntity())
        val timerUseCase = TimerUseCase(created.id, timerDataSource)

        val data = timerUseCase.fetch()

        assert(data.title == title)
        assert(data.description == description)
        assert(data.seconds == seconds)
        assert(data.minutes == minutes)
    }


    @Test
    fun editDataTest() {
        timerDataSource.deleteAll()
        val created = timerDataSource.insertTimer(createTimerEntity())
        val timerUseCase = TimerUseCase(created.id, timerDataSource)

        val data = timerUseCase.editTime(40, 20)

        assert(data.title == title)
        assert(data.description == description)
        assert(data.seconds == 20)
        assert(data.minutes == 40)
    }

}