package com.sesang06.foodtimer

import androidx.test.platform.app.InstrumentationRegistry
import com.sesang06.foodtimer.database.AppInstalledRepository
import com.sesang06.foodtimer.database.TimerDataSource
import com.sesang06.foodtimer.database.TimerEntity
import com.sesang06.foodtimer.database.TimerUseCase
import org.junit.Test

class TimerUseCaseTest {


    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    val appInstalledRepository = AppInstalledRepository(appContext)

    val timerDataSource = TimerDataSource(appContext)

    val timerUseCase = TimerUseCase(appInstalledRepository, timerDataSource)

    companion object {
        const val title = "EGG"
        const val description = "3분"
        const val minutes = 40
        const val seconds = 10

    }
    fun createTimerEntity() = TimerEntity(
        0,
        title, description, minutes, seconds
    )

    @Test
    fun fetchAllDataTest() {

        timerDataSource.deleteAll()

        timerDataSource.insertTimer(createTimerEntity())

        val data = timerUseCase.fetchData()

        assert(data.size == 1)
        val item = data[0]
        assert(item.title == title)
        assert(item.minutes == minutes)
        assert(item.seconds == seconds)
    }

    @Test
    fun createDefaultDataTest() {

        timerDataSource.deleteAll()

        timerUseCase.createDefaultData()

        val data = timerUseCase.fetchData()
        assert(data.size == 3)
        assert(data[0].title == "계란 반숙")
        assert(data[1].title == "컵라면")
        assert(data[2].title == "파스타")
    }
}